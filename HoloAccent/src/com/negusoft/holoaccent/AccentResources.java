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
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.TypedValue;

import com.negusoft.holoaccent.interceptor.CircleInterceptor;
import com.negusoft.holoaccent.interceptor.FastScrollInterceptor;
import com.negusoft.holoaccent.interceptor.IndeterminateInterceptor;
import com.negusoft.holoaccent.interceptor.RectInterceptor;
import com.negusoft.holoaccent.interceptor.RoundRectInterceptor;
import com.negusoft.holoaccent.interceptor.ScrubberInterceptor;
import com.negusoft.holoaccent.interceptor.SearchViewTextFieldInterceptor;
import com.negusoft.holoaccent.interceptor.SolidColorInterceptor;
import com.negusoft.holoaccent.interceptor.SpinnerInterceptor;
import com.negusoft.holoaccent.interceptor.ToggleInterceptor;
import com.negusoft.holoaccent.interceptor.UnderlineInterceptor;
import com.negusoft.holoaccent.util.BitmapUtils;
import com.negusoft.holoaccent.util.NativeResources;

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
		R.drawable.ha__textfield_comp_activated_left,
		R.drawable.ha__textfield_comp_activated_main,
		R.drawable.ha__textfield_comp_activated_right,
		R.drawable.ha__textfield_comp_focused_left,
		R.drawable.ha__textfield_comp_focused_main,
		R.drawable.ha__textfield_comp_focused_right,
		R.drawable.ha__btn_check_comp_off_focus,
		R.drawable.ha__btn_check_comp_on_focus,
		R.drawable.ha__progress_comp_primary
	};
	
	private static final int[] TINT_TRANSFORMATION_DRAWABLE_IDS = new int[] {
		R.drawable.ha__text_select_handle_middle_transformation,
		R.drawable.ha__text_select_handle_left_transformation,
		R.drawable.ha__text_select_handle_right_transformation,
		R.drawable.ha__btn_check_on_transformation,
		R.drawable.ha__btn_check_on_transformation_light,
		R.drawable.ha__btn_radio_comp_dot_transformation
	};

	private final Context mContext;
	private final int mExplicitColor;
	private final int mExplicitColorDark;
	
	private boolean mInitialized = false;
	private AccentPalette mPalette;
	private List<Interceptor> mIterceptors;
	
	public AccentResources(Context c, Resources resources) {
		super(resources.getAssets(), resources.getDisplayMetrics(), resources.getConfiguration());
		mContext = c;
		mExplicitColor = 0;
		mExplicitColorDark = 0;
	}
	
	public AccentResources(Context c, Resources resources, int color) {
		super(resources.getAssets(), resources.getDisplayMetrics(), resources.getConfiguration());
		mContext = c;
		mExplicitColor = color;
		mExplicitColorDark = 0;
	}
	
	public AccentResources(Context c, Resources resources, int color, int colorDark) {
		super(resources.getAssets(), resources.getDisplayMetrics(), resources.getConfiguration());
		mContext = c;
		mExplicitColor = color;
		mExplicitColorDark = colorDark;
	}
	
	/**
	 * Make sure that the instance is initialized. It will check the 'mInitialized' 
	 * flag even if it is done within 'initialize()', to avoid getting into the 
	 * synchronized block every time.
	 */
	private void checkInitialized() {
		if (mInitialized)
			return;
		initialize(mContext, mExplicitColor, mExplicitColorDark);
	}
	
	private synchronized void initialize(Context c, int explicitColor, int explicitColorDark) {
		if (mInitialized)
			return;
		mPalette = initPalette(c, explicitColor, explicitColorDark);
		mIterceptors = new ArrayList<Interceptor>();
		addInterceptors(c);
		mInitialized = true;
	}
	
	private AccentPalette initPalette(Context c, int explicitColor, int explicitColorDark) {
		TypedArray attrs = c.getTheme().obtainStyledAttributes(R.styleable.HoloAccent);
		
		int color = explicitColor != 0 ? explicitColor : 
			attrs.getColor(R.styleable.HoloAccent_accentColor, getColor(android.R.color.holo_blue_light));
		int colorDark = explicitColorDark != 0 ? explicitColorDark : 
			attrs.getColor(R.styleable.HoloAccent_accentColorDark, 0);
		
		return new AccentPalette(color, colorDark);
	}
	
	private void addInterceptors(Context c) {
		mIterceptors.add(new ToggleInterceptor());
		mIterceptors.add(new UnderlineInterceptor());
		mIterceptors.add(new SolidColorInterceptor());
		mIterceptors.add(new RectInterceptor());
		mIterceptors.add(new RoundRectInterceptor());
		mIterceptors.add(new CircleInterceptor());
		mIterceptors.add(new ScrubberInterceptor());
		mIterceptors.add(new FastScrollInterceptor());
		mIterceptors.add(new IndeterminateInterceptor());
		mIterceptors.add(new SpinnerInterceptor());
		mIterceptors.add(new SearchViewTextFieldInterceptor());
		mIterceptors.add(new OverScrollInterceptor());
	}

	@Override
	public Drawable getDrawable(int resId) throws Resources.NotFoundException {
		checkInitialized();
		
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
		checkInitialized();
		
		for (int id : TINT_DRAWABLE_IDS) {
			if (resId == id)
				return getTintendResourceStream(resId, value, mPalette.accentColor);
		}
		for (int id : TINT_TRANSFORMATION_DRAWABLE_IDS) {
			if (resId == id)
				return getTintTransformationResourceStream(resId, value, mPalette.accentColor);
		}
		return super.openRawResource(resId, value);
	}
	
	/**
	 * Get a reference to a resource that is equivalent to the one requested, 
	 * but with the accent color applied to it.
	 */
	private InputStream getTintendResourceStream(int id, TypedValue value, int color) {
		checkInitialized();

		Bitmap bitmap = getBitmapFromResource(id, value);
		bitmap = BitmapUtils.applyColor(bitmap, color);
		return getStreamFromBitmap(bitmap);
	}
	
	/**
	 * Get a reference to a resource that is equivalent to the one requested, 
	 * but changing the tint from the original red to the given color.
	 */
	private InputStream getTintTransformationResourceStream(int id, TypedValue value, int color) {
		checkInitialized();
		
		Bitmap bitmap = getBitmapFromResource(id, value);
		bitmap = BitmapUtils.processTintTransformationMap(bitmap, color);
		return getStreamFromBitmap(bitmap);
	}
	
	private Bitmap getBitmapFromResource(int resId, TypedValue value) {
		InputStream original = super.openRawResource(resId, value);
		value.density = getDisplayMetrics().densityDpi;
		final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inDither = false;
        options.inScaled = false;
        options.inScreenDensity = getDisplayMetrics().densityDpi;
		return BitmapFactory.decodeResourceStream(
				this, value, original, 
				new Rect(), options);
	}
	
	private InputStream getStreamFromBitmap(Bitmap bitmap) {
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

		private static final String RESOURCE_NAME_EDGE = "overscroll_edge";
		private static final String RESOURCE_NAME_GLOW = "overscroll_glow";

		private final int mOverscrollEdgeId;
		private final int mOverscrollGlowId;
		
		public OverScrollInterceptor() {
            mOverscrollEdgeId = NativeResources.getDrawableIdentifier(RESOURCE_NAME_EDGE);
            mOverscrollGlowId = NativeResources.getDrawableIdentifier(RESOURCE_NAME_GLOW);
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
			Drawable result = AccentResources.super.getDrawable(R.drawable.ha__overscroll_edge);
			result.setColorFilter(mPalette.accentColor, PorterDuff.Mode.MULTIPLY);
			return result;
		}
		
		private Drawable getGlowDrawable() {
			Drawable result = AccentResources.super.getDrawable(R.drawable.ha__overscroll_glow);
			result.setColorFilter(mPalette.accentColor, PorterDuff.Mode.MULTIPLY);
			return result;
		}
	}

}
