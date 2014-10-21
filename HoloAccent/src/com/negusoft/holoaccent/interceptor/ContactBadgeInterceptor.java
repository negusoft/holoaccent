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
import com.negusoft.holoaccent.drawable.ContactBadgeDrawable;

public class ContactBadgeInterceptor implements AccentResources.Interceptor {

    private static final int FILL_COLOR_DARK = 0xccaaaaaa;
    private static final int LINE_COLOR_DARK = 0xcc090909;
    private static final int FILL_COLOR_LIGHT = 0x96333333;
    private static final int LINE_COLOR_LIGHT = 0xccffffff;
	
	@Override
	public Drawable getDrawable(Resources res, AccentPalette palette, int resId) {
        if (resId == R.drawable.ha__contact_badge_normal_reference)
            return new ContactBadgeDrawable(res, 0, 0f, 0, FILL_COLOR_DARK, LINE_COLOR_DARK);
        if (resId == R.drawable.ha__contact_badge_focused_reference) {
            int backColor = palette.getActionBarAccentColor(0x55);
            int borderColor = palette.getActionBarAccentColor(0xAA);
            return new ContactBadgeDrawable(res, backColor, 2f, borderColor, palette.accentColor, LINE_COLOR_DARK);
        }
        if (resId == R.drawable.ha__contact_badge_pressed_reference) {
            int borderColor = palette.getActionBarAccentColor(0xAA);
            return new ContactBadgeDrawable(res, borderColor, 0f, 0, palette.accentColor, LINE_COLOR_DARK);
        }

        if (resId == R.drawable.ha__contact_badge_normal_light_reference)
            return new ContactBadgeDrawable(res, 0, 0f, 0, FILL_COLOR_LIGHT, LINE_COLOR_LIGHT);
        if (resId == R.drawable.ha__contact_badge_focused_light_reference) {
            int backColor = palette.getActionBarAccentColor(0x55);
            int borderColor = palette.getActionBarAccentColor(0xAA);
            return new ContactBadgeDrawable(res, backColor, 2f, borderColor, palette.accentColor, LINE_COLOR_LIGHT);
        }
        if (resId == R.drawable.ha__contact_badge_pressed_light_reference) {
            int borderColor = palette.getActionBarAccentColor(0xAA);
            return new ContactBadgeDrawable(res, borderColor, 0f, 0, palette.accentColor, LINE_COLOR_LIGHT);
        }

		return null;
	}

}
