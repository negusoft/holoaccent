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
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;

import com.negusoft.holoaccent.AccentPalette;
import com.negusoft.holoaccent.AccentResources;
import com.negusoft.holoaccent.R;

public class PagerTabStripInterceptor implements AccentResources.Interceptor {

	private static final int DEFAULT_ALPHA = 96;
	private static final int LIGHT_ALPHA = 48;

	@Override
	public Drawable getDrawable(Resources res, AccentPalette palette, int resId) {
		if (resId == R.drawable.ha__pager_tab_strip_background_reference)
			return new ColorDrawable(palette.getDarkAccentColor(DEFAULT_ALPHA));
		if (resId == R.drawable.ha__pager_tab_strip_background_light_reference)
			return new ColorDrawable(palette.getDarkAccentColor(LIGHT_ALPHA));
		return null;
	}

}
