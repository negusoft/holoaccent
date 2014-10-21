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
import android.view.View;
import android.view.Window;

import com.negusoft.holoaccent.AccentHelper;
import com.negusoft.holoaccent.AccentPalette;
import com.negusoft.holoaccent.util.NativeResources;

/**
 * Utility class to change the color of the divider color of dialogs. To do so, it finds the
 * view using the native identifier in the layout and changes the background.
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
        AccentPalette palette = AccentHelper.getPalette(c);
        if (palette == null)
            return c.getResources().getColor(android.R.color.holo_blue_light);
        return palette.getAccentColor();
    }

    public int getColor() {
        return mColor;
    }

    public void setColor(int color) {
        mColor = color;
    }
	
	public void paint(Window window) {
        // Paint divider
        int id = NativeResources.getIdentifier(DIVIDER_IDENTIFIER_NAME);
		View divider = window.findViewById(id);
		if (divider != null)
            divider.setBackgroundColor(mColor);
	}
}
