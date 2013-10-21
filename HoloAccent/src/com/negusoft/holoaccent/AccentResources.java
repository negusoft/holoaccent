/*
 * Copyright (C) 2012 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.negusoft.holoaccent;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.util.DisplayMetrics;
import android.util.TypedValue;

import com.negusoft.holoaccent.drawable.IndeterminedProgressDrawable;
import com.negusoft.holoaccent.drawable.RectDrawable;
import com.negusoft.holoaccent.drawable.ToggleForegroundDrawable;
import com.negusoft.holoaccent.drawable.UnderlineDrawable;
import com.negusoft.holoaccent.util.BitmapUtils;

/**
 * Extends the default android Resources to replace and modify 
 * drawables on runtime and apply the accent color.
 * <br/><br/>
 * "openRawResource()" is called when inflating XML drawable 
 * resources. By overriding it, we can replace the components 
 * that form the drawable.
 * <br/><br/>
 * For the OverScroll, the native android drawables are modified 
 * directly. We look up their id by name, and the we replace the 
 * drawable with a tinted version by applying a ColorFilter.
 */
public class AccentResources extends Resources {
	
	private static final int[] TINT_DRAWABLE_IDS = new int[] {
		R.drawable.textfield_comp_activated_left,
		R.drawable.textfield_comp_activated_main,
		R.drawable.textfield_comp_activated_right,
		R.drawable.textfield_comp_focused_left,
		R.drawable.textfield_comp_focused_main,
		R.drawable.textfield_comp_focused_right,
		R.drawable.btn_check_comp_on_accent,
		R.drawable.btn_check_comp_off_focus,
		R.drawable.btn_check_comp_on_focus,
		R.drawable.progress_comp_primary,
		R.drawable.progress_comp_primary
	};

//	private final int mAccentColor;
	private final AccentPalette mAccentColor;

	private final ToggleInterceptor mToggleInterceptor;
	private final UnderlineInterceptor mUnderlineInterceptor;
	private final SolidColorInterceptor mSolidColorInterceptor;
	private final RectInterceptor mRectInterceptor;
	private final IndeterminateInterceptor mIndeterminateInterceptor;
	private final OverScrollIntercepter mOverScrollInterceptor;

	public AccentResources(int accentColor, AssetManager assets, DisplayMetrics metrics, Configuration config) {
		super(assets, metrics, config);
		mAccentColor = new AccentPalette(accentColor);
		mToggleInterceptor = new ToggleInterceptor();
		mUnderlineInterceptor = new UnderlineInterceptor();
		mSolidColorInterceptor = new SolidColorInterceptor();
		mRectInterceptor = new RectInterceptor();
		mIndeterminateInterceptor = new IndeterminateInterceptor();
		mOverScrollInterceptor = new OverScrollIntercepter();
	}

	public AccentResources(Context c, AssetManager assets, DisplayMetrics metrics, Configuration config) {
		super(assets, metrics, config);
		
		TypedArray attrs = c.getTheme().obtainStyledAttributes(R.styleable.HoloAccent);
		int accentColor = attrs.getColor(R.styleable.HoloAccent_accentColor, getColor(android.R.color.holo_blue_light));
		mAccentColor = new AccentPalette(accentColor);
		attrs.recycle();

		mToggleInterceptor = new ToggleInterceptor();
		mUnderlineInterceptor = new UnderlineInterceptor();
		mSolidColorInterceptor = new SolidColorInterceptor();
		mRectInterceptor = new RectInterceptor();
		mIndeterminateInterceptor = new IndeterminateInterceptor();
		mOverScrollInterceptor = new OverScrollIntercepter();
	}

	@Override
	public Drawable getDrawable(int resId) throws Resources.NotFoundException {
		if (resId == R.drawable.progress_comp_primary) {
			return super.getDrawable(resId);
		}
		
		// Replace the toggle button foreground drawables if required
		Drawable toggleDrawable = mToggleInterceptor.getDrawable(resId);
		if (toggleDrawable != null)
			return toggleDrawable;
		
		// Replace the underline drawables
		Drawable underlineDrawable = mUnderlineInterceptor.getDrawable(resId);
		if (underlineDrawable != null)
			return underlineDrawable;
		
		// Replace the underline drawables
		Drawable solidColorDrawable = mSolidColorInterceptor.getDrawable(resId);
		if (solidColorDrawable != null)
			return solidColorDrawable;
		
		// Replace the underline drawables
		Drawable rectColorDrawable = mRectInterceptor.getDrawable(resId);
		if (rectColorDrawable != null)
			return rectColorDrawable;
		
		// Replace the indetermined horizontal drawables if required
		Drawable indeterminedDrawable = mIndeterminateInterceptor.getDrawable(resId);
		if (indeterminedDrawable != null)
			return indeterminedDrawable;
		
		// Give the OverScroll intercepter a chance to override the drawable
		Drawable overScrollDrawable = mOverScrollInterceptor.getDrawable(resId);
		if (overScrollDrawable != null)
			return overScrollDrawable;
		
		return super.getDrawable(resId);
	}
	
	@Override
	public InputStream openRawResource(int resId, TypedValue value)
			throws NotFoundException {
		for (int id : TINT_DRAWABLE_IDS) {
			if (resId == id)
				return getTintendResourceStream(resId, value);
		}
		return super.openRawResource(resId, value);
	}
	
	/**
	 * Get a reference to a resource that is equivalent to the one requested, 
	 * but with the accent color applied to it.
	 */
	private InputStream getTintendResourceStream(int id, TypedValue value) {
		// Get the bitmap form the resources
		InputStream original = super.openRawResource(id, value);
		value.density = getDisplayMetrics().densityDpi;
		final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inDither = false;
        options.inScaled = false;
        options.inScreenDensity = getDisplayMetrics().densityDpi;
		Bitmap bitmap = BitmapFactory.decodeResourceStream(
				this, value, original, 
				new Rect(), options);
		
		// Apply the tint color
		bitmap = BitmapUtils.applyColor(bitmap, mAccentColor.accentColor);

		// Get the InputStream for the bitmap
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		bitmap.compress(CompressFormat.PNG, 100 /*ignored for PNG*/, bos);
		byte[] bitmapData = bos.toByteArray();
		try {
			bos.close();
		} catch (IOException e) { /* ignore */}
		
		return new ByteArrayInputStream(bitmapData);
	}
	
	/**
	 * Inner class holding the logic for replacing the toogle button's foreground 
	 * light that represents the state of the button
	 */
	private class ToggleInterceptor {

		private final int COLOR_ON_PRESSED = Color.rgb(255, 255, 255);
		private final int COLOR_OFF = Color.argb(128, 0, 0, 0);
		private final int COLOR_OFF_DISABLED = Color.argb(64, 0, 0, 0);

		public Drawable getDrawable(int resId) {
			if (resId == R.drawable.btn_toggle_comp_on_foreground)
				return new ToggleForegroundDrawable(AccentResources.this, mAccentColor.accentColor);
			if (resId == R.drawable.btn_toggle_comp_on_foreground_pressed)
				return new ToggleForegroundDrawable(AccentResources.this, COLOR_ON_PRESSED);
			if (resId == R.drawable.btn_toggle_comp_on_foreground_disabled)
				return new ToggleForegroundDrawable(AccentResources.this, mAccentColor.getTranslucent(128));
			if (resId == R.drawable.btn_toggle_comp_off_foreground)
				return new ToggleForegroundDrawable(AccentResources.this, COLOR_OFF);
			if (resId == R.drawable.btn_toggle_comp_off_foreground_disabled)
				return new ToggleForegroundDrawable(AccentResources.this, COLOR_OFF_DISABLED);
			return null;
		}
	}
	
	/** Inner class holding the logic for replacing underline drawables */
	private class UnderlineInterceptor {

		public Drawable getDrawable(int resId) {
			if (resId == R.drawable.underline_1_5)
				return new UnderlineDrawable(AccentResources.this, mAccentColor.accentColor, 1.5f);
			if (resId == R.drawable.underline_6)
				return new UnderlineDrawable(AccentResources.this, mAccentColor.accentColor, 6f);
			return null;
		}
	}
	
	/** Inner class holding the logic for replacing solid color drawables */
	private class SolidColorInterceptor {
		private static final int PRESSED_ALPHA = 0xAA;
		private static final int FOCUSED_ALPHA = 0x55;

		public Drawable getDrawable(int resId) {
			if (resId == R.drawable.solid_pressed)
				return new ColorDrawable(mAccentColor.getTranslucent(PRESSED_ALPHA));
			if (resId == R.drawable.solid_focused)
				return new ColorDrawable(mAccentColor.getTranslucent(FOCUSED_ALPHA));
			return null;
		}
	}
	
	/** Inner class holding the logic for replacing rectangle drawables */
	private class RectInterceptor {
		public Drawable getDrawable(int resId) {
			if (resId == R.drawable.rect_focused_background) {
				int backColor = mAccentColor.getTranslucent(0x55);
				int borderColor = mAccentColor.getTranslucent(0xAA);
				return new RectDrawable(AccentResources.this, backColor, 2f, borderColor);
			}
			return null;
		}
	}
	
	/**
	 * Inner class holding the logic for replacing the indeterminate 
	 * horizontal progress bar drawables.
	 */
	private class IndeterminateInterceptor {

		private final int[] INDETERMINED_DRAWABLE_IDS = new int[] {
			R.drawable.progressbar_indeterminate_1,
			R.drawable.progressbar_indeterminate_2,
			R.drawable.progressbar_indeterminate_3,
			R.drawable.progressbar_indeterminate_4,
			R.drawable.progressbar_indeterminate_5,
			R.drawable.progressbar_indeterminate_6,
			R.drawable.progressbar_indeterminate_7,
			R.drawable.progressbar_indeterminate_8
		};

		public Drawable getDrawable(int resId) {
			for (int i=0; i< INDETERMINED_DRAWABLE_IDS.length; i++) {
				if (resId == INDETERMINED_DRAWABLE_IDS[i])
					return new IndeterminedProgressDrawable(AccentResources.this, mAccentColor.accentColor, i);
			}
			return null;
		}
	}
	
	/**
	 * Inner class holding the logic for applying a ColorFilter to the OverScroll 
	 * drawables.
	 */
	private class OverScrollIntercepter {

		private static final String RESOURCE_TYPE = "drawable";
		private static final String RESOURCE_PACKAGE = "android";
		private static final String RESOURCE_NAME_EDGE = "overscroll_edge";
		private static final String RESOURCE_NAME_GLOW = "overscroll_glow";

		private final int mOverscrollEdgeId;
		private final int mOverscrollGlowId;
		
		public OverScrollIntercepter() {
			mOverscrollEdgeId = getIdentifier(RESOURCE_NAME_EDGE, RESOURCE_TYPE, RESOURCE_PACKAGE);
			mOverscrollGlowId = getIdentifier(RESOURCE_NAME_GLOW, RESOURCE_TYPE, RESOURCE_PACKAGE);
		}

		public Drawable getDrawable(int resId) {
			if (resId == mOverscrollEdgeId)
				return getEdgeDrawable();
			if (resId == mOverscrollGlowId)
				return getGlowDrawable();
			return null;
		}
		
		private Drawable getEdgeDrawable() {
			Drawable result = AccentResources.super.getDrawable(R.drawable.overscroll_edge);
			result.setColorFilter(mAccentColor.accentColor, PorterDuff.Mode.MULTIPLY);
			return result;
		}
		
		private Drawable getGlowDrawable() {
			Drawable result = AccentResources.super.getDrawable(R.drawable.overscroll_glow);
			result.setColorFilter(mAccentColor.accentColor, PorterDuff.Mode.MULTIPLY);
			return result;
		}
	}

}
