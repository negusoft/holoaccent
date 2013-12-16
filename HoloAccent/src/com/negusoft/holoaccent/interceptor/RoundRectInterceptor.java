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
import com.negusoft.holoaccent.drawable.RoundRectDrawable;

public class RoundRectInterceptor implements AccentResources.Interceptor {

	private static final float BORDER_WIDTH_DP = 2.0f;
	private static final float BORDER_WIDTH_DISABLED_DP = 0.8f;
	private static final float CORNER_RADIUS_DP = 3f;
	private static final float BUTTION_GLOW_CORNER_RADIUS_DP = 10f;
	
	@Override
	public Drawable getDrawable(Resources res, AccentPalette palette, int resId) {
		if (resId == R.drawable.ha__roundrect_check_pressed)
			return new RoundRectDrawable(res.getDisplayMetrics(), palette.getAccentColor(0x88), CORNER_RADIUS_DP);
		if (resId == R.drawable.ha__roundrect_spinner_pressed)
			return new RoundRectDrawable(res.getDisplayMetrics(), palette.getAccentColor(0xAA), CORNER_RADIUS_DP);
		if (resId == R.drawable.ha__roundrect_spinner_focussed)
			return new RoundRectDrawable(res.getDisplayMetrics(), palette.getAccentColor(0x55), BORDER_WIDTH_DP, palette.getAccentColor(0xAA), CORNER_RADIUS_DP);
		if (resId == R.drawable.ha__roundrect_button_pressed_glow)
			return new RoundRectDrawable(res.getDisplayMetrics(), palette.getAccentColor(0x55), BUTTION_GLOW_CORNER_RADIUS_DP);
		if (resId == R.drawable.ha__roundrect_button_pressed_fill)
			return new RoundRectDrawable(res.getDisplayMetrics(), palette.accentColor, CORNER_RADIUS_DP);
		if (resId == R.drawable.ha__roundrect_button_pressed_fill_colored)
			return new RoundRectDrawable(res.getDisplayMetrics(), palette.getAccentColor(0x55), CORNER_RADIUS_DP);
		if (resId == R.drawable.ha__roundrect_button_focused)
			return new RoundRectDrawable(res.getDisplayMetrics(), palette.getAccentColor(0x66), BORDER_WIDTH_DP, palette.accentColor, CORNER_RADIUS_DP);
		if (resId == R.drawable.ha__roundrect_button_disabled_focused)
			return new RoundRectDrawable(res.getDisplayMetrics(), palette.getAccentColor(0x22), BORDER_WIDTH_DP, palette.getAccentColor(0x33), CORNER_RADIUS_DP);
		if (resId == R.drawable.ha__roundrect_button_normal_colored)
			return new RoundRectDrawable(res.getDisplayMetrics(), palette.getDarkAccentColor(), CORNER_RADIUS_DP);
		if (resId == R.drawable.ha__roundrect_button_normal_colored_bright)
			return new RoundRectDrawable(res.getDisplayMetrics(), palette.accentColor, CORNER_RADIUS_DP);
		if (resId == R.drawable.ha__roundrect_button_disabled_colored)
			return new RoundRectDrawable(res.getDisplayMetrics(), palette.getAccentColor(0x22), BORDER_WIDTH_DISABLED_DP, palette.getAccentColor(0x55), CORNER_RADIUS_DP);
		if (resId == R.drawable.ha__roundrect_button_disabled_focused_colored)
			return new RoundRectDrawable(res.getDisplayMetrics(), palette.getAccentColor(0x22), BORDER_WIDTH_DP, palette.getAccentColor(0x88), CORNER_RADIUS_DP);
		return null;
	}

}
