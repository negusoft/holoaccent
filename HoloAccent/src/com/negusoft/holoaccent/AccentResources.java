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
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.DisplayMetrics;
import android.util.TypedValue;

import com.negusoft.holoaccent.drawable.IndeterminedProgressDrawable;
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
	
	private final int mAccentColor;

	private final IndeterminateInterceptor mIndeterminateInterceptor;
	private final OverScrollIntercepter mOverScrollInterceptor;

	public AccentResources(int accentColor, AssetManager assets, DisplayMetrics metrics, Configuration config) {
		super(assets, metrics, config);
		mAccentColor = accentColor;
		mIndeterminateInterceptor = new IndeterminateInterceptor();
		mOverScrollInterceptor = new OverScrollIntercepter();
	}

	public AccentResources(Context c, AssetManager assets, DisplayMetrics metrics, Configuration config) {
		super(assets, metrics, config);
		
		TypedArray attrs = c.getTheme().obtainStyledAttributes(R.styleable.HoloAccent);
		mAccentColor = attrs.getColor(R.styleable.HoloAccent_accentColor, getColor(android.R.color.holo_blue_light));
		attrs.recycle();

		mIndeterminateInterceptor = new IndeterminateInterceptor();
		mOverScrollInterceptor = new OverScrollIntercepter();
	}

	@Override
	public Drawable getDrawable(int resId) throws Resources.NotFoundException {
		if (resId == R.drawable.progress_comp_primary) {
			return super.getDrawable(resId);
		}
		
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
	
//	private Drawable getFilteredDrawable(int id) {
//		Drawable result = super.getDrawable(id);
//		result.setColorFilter(mAccentColor, PorterDuff.Mode.MULTIPLY);
//		return result;
//	}
	
//	private Drawable getFilteredDrawable(int id) {
//		Bitmap bitmap = BitmapFactory.decodeResource(this, id);
//		bitmap = BitmapUtils.applyColor(bitmap, mAccentColor);
////		try {
////			BitmapUtils.writeToFile(bitmap, "holoaccent", id + ".png");
////		} catch (IOException e) {
////			e.printStackTrace();
////		}
//		return new BitmapDrawable(this, bitmap);
//	}
	
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
		bitmap = BitmapUtils.applyColor(bitmap, mAccentColor);

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
					return new IndeterminedProgressDrawable(AccentResources.this, mAccentColor, i);
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
			result.setColorFilter(mAccentColor, PorterDuff.Mode.MULTIPLY);
			return result;
		}
		
		private Drawable getGlowDrawable() {
			Drawable result = AccentResources.super.getDrawable(R.drawable.overscroll_glow);
			result.setColorFilter(mAccentColor, PorterDuff.Mode.MULTIPLY);
			return result;
		}
	}

}
