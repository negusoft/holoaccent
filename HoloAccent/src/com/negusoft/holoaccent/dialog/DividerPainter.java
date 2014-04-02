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
package com.negusoft.holoaccent.dialog;

import android.content.Context;
import android.content.res.TypedArray;
import android.view.View;
import android.view.Window;

import com.negusoft.holoaccent.R;
import com.negusoft.holoaccent.activity.AccentActivity;
import com.negusoft.holoaccent.util.NativeResources;

/**
 * Utility class to change the color of the divider color of dialogs. To do so, it finds the
 * view using the native identifier in the layout and changes the background. If specified in
 * the constructor, the title text color will also be changed.
 */
public class DividerPainter {

    private static final String DIVIDER_IDENTIFIER_NAME = "titleDivider";

    private int mColor;

    public DividerPainter(int color) {
        mColor = color;
    }

	public DividerPainter(Context c) {
		mColor = initColor(c);
	}

    private int initColor(Context c) {
        // If the context is AccentActivity, check whether the accent color is set in code
        if (c instanceof AccentActivity) {
            int overrideColor = ((AccentActivity)c).getOverrideAccentColor();
            if (overrideColor != 0)
                return overrideColor;
        }
        // Get the accent color from the theme
        TypedArray attrs = c.getTheme().obtainStyledAttributes(R.styleable.HoloAccent);
        int defaultColor = c.getResources().getColor(android.R.color.holo_blue_light);
        int result = attrs.getColor(R.styleable.HoloAccent_accentColor, defaultColor);
        attrs.recycle();
        return result;
    }

    public int getColor() {
        return mColor;
    }

    public void setColor(int color) {
        mColor = color;
    }
	
	public final void paint(Window window) {
        // Paint divider
        int id = NativeResources.getIdentifier(DIVIDER_IDENTIFIER_NAME);
		View divider = window.findViewById(id);
		if (divider != null)
            divider.setBackgroundColor(mColor);
	}
}
