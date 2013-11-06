/*******************************************************************************
 * Copyright 2013 NEGU Soft
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package com.negusoft.holoaccent;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

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

import com.negusoft.holoaccent.intercepter.CircleInterceptor;
import com.negusoft.holoaccent.intercepter.FastScrollInterceptor;
import com.negusoft.holoaccent.intercepter.IndeterminateInterceptor;
import com.negusoft.holoaccent.intercepter.RadioOnInterceptor;
import com.negusoft.holoaccent.intercepter.RectInterceptor;
import com.negusoft.holoaccent.intercepter.RoundRectInterceptor;
import com.negusoft.holoaccent.intercepter.ScrubberInterceptor;
import com.negusoft.holoaccent.intercepter.SolidColorInterceptor;
import com.negusoft.holoaccent.intercepter.TextSelectHandleInterceptor;
import com.negusoft.holoaccent.intercepter.ToggleInterceptor;
import com.negusoft.holoaccent.intercepter.UnderlineInterceptor;
import com.negusoft.holoaccent.util.BitmapUtils;

/**
 * Extends the default android Resources to replace and modify 
 * drawables at runtime and apply the accent color.
 * <br/><br/>
 * "openRawResource()" and "getDrawable()" are called when inflating 
 * XML drawable resources. By overriding them, we can replace the 
 * components that form the drawables at runtime.
 * <br/><br/>
 * For the OverScroll, the native android drawables are modified 
 * directly. We look up their id by name, and then we replace the 
 * drawable with a tinted version by applying a ColorFilter.
 */
public class AccentResources extends Resources {
	
	public interface Interceptor {
		/**
		 * @return The drawable to be replaced or null 
		 * to continue the normal flow.
		 */
		public Drawable getDrawable(Resources res, AccentPalette palette, int resId);
	}
	
	private static final int[] TINT_DRAWABLE_IDS = new int[] {
		R.drawable.textfield_comp_activated_left,
		R.drawable.textfield_comp_activated_main,
		R.drawable.textfield_comp_activated_right,
		R.drawable.textfield_comp_focused_left,
		R.drawable.textfield_comp_focused_main,
		R.drawable.textfield_comp_focused_right,
		R.drawable.btn_check_comp_off_focus,
		R.drawable.btn_check_comp_on_focus,
		R.drawable.progress_comp_primary,
		R.drawable.scrubber_comp_primary,
		R.drawable.scrubber_comp_secondary
	};
	
	private static final int[] DARK_TINT_DRAWABLE_IDS = new int[] {
		R.drawable.btn_check_comp_on_accent
	};

	private final AccentPalette mPalette;
	private final List<Interceptor> mIterceptors;

	public AccentResources(int accentColor, AssetManager assets, DisplayMetrics metrics, Configuration config) {
		super(assets, metrics, config);
		mPalette = new AccentPalette(accentColor);
		mIterceptors = new ArrayList<Interceptor>();
		addInterceptors();
	}

	public AccentResources(Context c, AssetManager assets, DisplayMetrics metrics, Configuration config) {
		super(assets, metrics, config);
		
		TypedArray attrs = c.getTheme().obtainStyledAttributes(R.styleable.HoloAccent);
		int accentColor = attrs.getColor(R.styleable.HoloAccent_accentColor, getColor(android.R.color.holo_blue_light));
		mPalette = new AccentPalette(accentColor);
		attrs.recycle();

		mIterceptors = new ArrayList<Interceptor>();
		addInterceptors();
	}
	
	private void addInterceptors() {
		mIterceptors.add(new ToggleInterceptor());
		mIterceptors.add(new UnderlineInterceptor());
		mIterceptors.add(new SolidColorInterceptor());
		mIterceptors.add(new RectInterceptor());
		mIterceptors.add(new RoundRectInterceptor());
		mIterceptors.add(new CircleInterceptor());
		mIterceptors.add(new ScrubberInterceptor());
		mIterceptors.add(new TextSelectHandleInterceptor());
		mIterceptors.add(new FastScrollInterceptor());
		mIterceptors.add(new IndeterminateInterceptor());
		mIterceptors.add(new RadioOnInterceptor());
		mIterceptors.add(new OverScrollInterceptor());
	}

	@Override
	public Drawable getDrawable(int resId) throws Resources.NotFoundException {
		// Give a chance to the interceptors to replace the drawable
		Drawable result;
		for(Interceptor interceptor : mIterceptors) {
			result = interceptor.getDrawable(this, mPalette, resId);
			if (result != null)
				return result;
		}
		
		return super.getDrawable(resId);
	}
	
	@Override
	public InputStream openRawResource(int resId, TypedValue value)
			throws NotFoundException {
		for (int id : TINT_DRAWABLE_IDS) {
			if (resId == id)
				return getTintendResourceStream(resId, value, mPalette.accentColor);
		}
		for (int id : DARK_TINT_DRAWABLE_IDS) {
			if (resId == id)
				return getTintendResourceStream(resId, value, mPalette.getDarkAccentColor());
		}
		return super.openRawResource(resId, value);
	}
	
	/**
	 * Get a reference to a resource that is equivalent to the one requested, 
	 * but with the accent color applied to it.
	 */
	private InputStream getTintendResourceStream(int id, TypedValue value, int color) {
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
		bitmap = BitmapUtils.applyColor(bitmap, color);

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
	 * Inner class holding the logic for applying a ColorFilter to the OverScroll 
	 * drawables. It is implemented a an inner class because it needs to access 
	 * the parents implementation of getDrawable().
	 */
	private class OverScrollInterceptor implements Interceptor {

		private static final String RESOURCE_TYPE = "drawable";
		private static final String RESOURCE_PACKAGE = "android";
		private static final String RESOURCE_NAME_EDGE = "overscroll_edge";
		private static final String RESOURCE_NAME_GLOW = "overscroll_glow";

		private final int mOverscrollEdgeId;
		private final int mOverscrollGlowId;
		
		public OverScrollInterceptor() {
			mOverscrollEdgeId = getIdentifier(RESOURCE_NAME_EDGE, RESOURCE_TYPE, RESOURCE_PACKAGE);
			mOverscrollGlowId = getIdentifier(RESOURCE_NAME_GLOW, RESOURCE_TYPE, RESOURCE_PACKAGE);
		}

		@Override
		public Drawable getDrawable(Resources res, AccentPalette palette, int resId) {
			if (resId == mOverscrollEdgeId)
				return getEdgeDrawable();
			if (resId == mOverscrollGlowId)
				return getGlowDrawable();
			return null;
		}
		
		private Drawable getEdgeDrawable() {
			Drawable result = AccentResources.super.getDrawable(R.drawable.overscroll_edge);
			result.setColorFilter(mPalette.accentColor, PorterDuff.Mode.MULTIPLY);
			return result;
		}
		
		private Drawable getGlowDrawable() {
			Drawable result = AccentResources.super.getDrawable(R.drawable.overscroll_glow);
			result.setColorFilter(mPalette.accentColor, PorterDuff.Mode.MULTIPLY);
			return result;
		}
	}

}
