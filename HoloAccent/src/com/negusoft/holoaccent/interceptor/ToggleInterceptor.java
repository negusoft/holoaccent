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
import android.graphics.Color;
import android.graphics.drawable.Drawable;

import com.negusoft.holoaccent.AccentPalette;
import com.negusoft.holoaccent.AccentResources;
import com.negusoft.holoaccent.R;
import com.negusoft.holoaccent.drawable.ToggleForegroundDrawable;

public class ToggleInterceptor implements AccentResources.Interceptor {

	private final int COLOR_ON_PRESSED = Color.rgb(255, 255, 255);
	private final int COLOR_OFF = Color.argb(128, 0, 0, 0);
	private final int COLOR_OFF_DISABLED = Color.argb(64, 0, 0, 0);

	@Override
	public Drawable getDrawable(Resources res, AccentPalette palette, int resId) {
		if (resId == R.drawable.ha__btn_toggle_comp_on_foreground)
			return new ToggleForegroundDrawable(res, palette.accentColor);
		if (resId == R.drawable.ha__btn_toggle_comp_on_foreground_pressed)
			return new ToggleForegroundDrawable(res, COLOR_ON_PRESSED);
		if (resId == R.drawable.ha__btn_toggle_comp_on_foreground_disabled)
			return new ToggleForegroundDrawable(res, palette.getAccentColor(128));
		if (resId == R.drawable.ha__btn_toggle_comp_off_foreground)
			return new ToggleForegroundDrawable(res, COLOR_OFF);
		if (resId == R.drawable.ha__btn_toggle_comp_off_foreground_disabled)
			return new ToggleForegroundDrawable(res, COLOR_OFF_DISABLED);
		return null;
	}

}
