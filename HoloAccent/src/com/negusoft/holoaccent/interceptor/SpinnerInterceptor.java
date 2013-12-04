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
package com.negusoft.holoaccent.interceptor;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;

import com.negusoft.holoaccent.AccentPalette;
import com.negusoft.holoaccent.AccentResources;
import com.negusoft.holoaccent.R;
import com.negusoft.holoaccent.drawable.SpinnerDrawable;

public class SpinnerInterceptor implements AccentResources.Interceptor {

	private static final int COLOR_DEFAULT_DARK = 0x99cccccc;
	private static final int COLOR_DISABLED_DARK = 0x32cccccc;
	private static final int COLOR_DEFAULT_LIGHT = 0x96333333;
	private static final int COLOR_DISABLED_LIGHT = 0x32333333;
	
	@Override
	public Drawable getDrawable(Resources res, AccentPalette palette, int resId) {
		// Default
		if (resId == R.drawable.ha__spinner_indicator)
			return new SpinnerDrawable(res, COLOR_DEFAULT_DARK, SpinnerDrawable.Type.DEFAULT);
		if (resId == R.drawable.ha__spinner_indicator_disabled)
			return new SpinnerDrawable(res, COLOR_DISABLED_DARK, SpinnerDrawable.Type.DEFAULT);
		if (resId == R.drawable.ha__spinner_indicator_light)
			return new SpinnerDrawable(res, COLOR_DEFAULT_LIGHT, SpinnerDrawable.Type.DEFAULT);
		if (resId == R.drawable.ha__spinner_indicator_disabled_light)
			return new SpinnerDrawable(res, COLOR_DISABLED_LIGHT, SpinnerDrawable.Type.DEFAULT);
		
		// Inverse (right to left)
		if (resId == R.drawable.ha__spinner_indicator_rtl)
			return new SpinnerDrawable(res, COLOR_DEFAULT_DARK, SpinnerDrawable.Type.DEFAULT_INVERSE);
		if (resId == R.drawable.ha__spinner_indicator_rtl_disabled)
			return new SpinnerDrawable(res, COLOR_DISABLED_DARK, SpinnerDrawable.Type.DEFAULT_INVERSE);
		if (resId == R.drawable.ha__spinner_indicator_rtl_light)
			return new SpinnerDrawable(res, COLOR_DEFAULT_LIGHT, SpinnerDrawable.Type.DEFAULT_INVERSE);
		if (resId == R.drawable.ha__spinner_indicator_rtl_disabled_light)
			return new SpinnerDrawable(res, COLOR_DISABLED_LIGHT, SpinnerDrawable.Type.DEFAULT_INVERSE);
		
		// ActionBar - Default
		if (resId == R.drawable.ha__ab_spinner_indicator)
			return new SpinnerDrawable(res, COLOR_DEFAULT_DARK, SpinnerDrawable.Type.ACTIONBAR);
		if (resId == R.drawable.ha__ab_spinner_indicator_disabled)
			return new SpinnerDrawable(res, COLOR_DISABLED_DARK, SpinnerDrawable.Type.ACTIONBAR);
		if (resId == R.drawable.ha__ab_spinner_indicator_light)
			return new SpinnerDrawable(res, COLOR_DEFAULT_LIGHT, SpinnerDrawable.Type.ACTIONBAR);
		if (resId == R.drawable.ha__ab_spinner_indicator_disabled_light)
			return new SpinnerDrawable(res, COLOR_DISABLED_LIGHT, SpinnerDrawable.Type.ACTIONBAR);
		
		// ActionBar - Inverse (right to left)
		if (resId == R.drawable.ha__ab_spinner_indicator_rtl)
			return new SpinnerDrawable(res, COLOR_DEFAULT_DARK, SpinnerDrawable.Type.ACTIONBAR_INVERSE);
		if (resId == R.drawable.ha__ab_spinner_indicator_rtl_disabled)
			return new SpinnerDrawable(res, COLOR_DISABLED_DARK, SpinnerDrawable.Type.ACTIONBAR_INVERSE);
		if (resId == R.drawable.ha__ab_spinner_indicator_rtl_light)
			return new SpinnerDrawable(res, COLOR_DEFAULT_LIGHT, SpinnerDrawable.Type.ACTIONBAR_INVERSE);
		if (resId == R.drawable.ha__ab_spinner_indicator_rtl_disabled_light)
			return new SpinnerDrawable(res, COLOR_DISABLED_LIGHT, SpinnerDrawable.Type.ACTIONBAR_INVERSE);
		
		return null;
	}

}
