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
import com.negusoft.holoaccent.drawable.RadialGradientDrawable;
import com.negusoft.holoaccent.drawable.RadioOnDrawable;

public class RadioInterceptor implements AccentResources.Interceptor {

	private static final int DISABLED_GLOW_COLOR = 0x22ffffff;
	private static final float DISABLED_GLOW_INNER_RADIUS_DP = 4.5f;
	private static final float DISABLED_GLOW_OUTER_RADIUS_DP = 8.5f;
	
	@Override
	public Drawable getDrawable(Resources res, AccentPalette palette, int resId) {
		if (resId == R.drawable.ha__gradient_radio_on_disabled)
			return new RadialGradientDrawable(res.getDisplayMetrics(), DISABLED_GLOW_COLOR, DISABLED_GLOW_INNER_RADIUS_DP, DISABLED_GLOW_OUTER_RADIUS_DP);
		if (resId == R.drawable.ha__radio_on_dot)
			return new RadioOnDrawable(res.getDisplayMetrics(), palette);
		return null;
	}

}
