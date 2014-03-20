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
import android.widget.TextView;

import com.negusoft.holoaccent.R;
import com.negusoft.holoaccent.util.NativeResources;

/**
 * Utility class to change the color of the divider color of dialogs. To do so, it finds the
 * view using the native identifier in the layout and changes the background. If specified in
 * the constructor, the title text color will also be changed.
 */
public class DividerPainter {

    private static final String DIVIDER_IDENTIFIER_NAME = "titleDivider";
    private static final String TITLE_ALERT_IDENTIFIER_NAME = "alertTitle";
    private static final String TITLE_DIALOG_IDENTIFIER_NAME = "title_container";

    private int mColor;
    private boolean mPaintTitle;

    public DividerPainter(int color) {
        mColor = color;
        mPaintTitle = false;
    }

    public DividerPainter(int color, boolean paintTitle) {
        mColor = color;
        mPaintTitle = paintTitle;
    }

	public DividerPainter(Context c) {
		TypedArray attrs = c.getTheme().obtainStyledAttributes(R.styleable.HoloAccent);
		int defaultColor = c.getResources().getColor(android.R.color.holo_blue_light);
		mColor = attrs.getColor(R.styleable.HoloAccent_accentColor, defaultColor);
		attrs.recycle();
        mPaintTitle = false;
	}

    public int getColor() {
        return mColor;
    }

    public void setColor(int color) {
        mColor = color;
    }

    public boolean getPaintTitle() {
        return mPaintTitle;
    }

    public void setPaintTitle(boolean paintTitle) {
        mPaintTitle = paintTitle;
    }
	
	public final void paint(Window window) {
        // Paint divider
        int id = NativeResources.getIdentifier(DIVIDER_IDENTIFIER_NAME);
		View divider = window.findViewById(id);
		if (divider != null)
            divider.setBackgroundColor(mColor);

        // Paint title text if required
        if (mPaintTitle) {
            id = NativeResources.getIdentifier(TITLE_ALERT_IDENTIFIER_NAME);
            TextView title = (TextView)window.findViewById(id);
            if (title != null)
                title.setTextColor(mColor);

//            id = NativeResources.getIdentifier(TITLE_DIALOG_IDENTIFIER_NAME);
            TextView view = (TextView)window.findViewById(android.R.id.title);
            if (view != null)
                view.setTextColor(mColor);
        }
	}
}
