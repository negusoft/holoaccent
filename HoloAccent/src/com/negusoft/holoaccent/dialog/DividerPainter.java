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
import com.negusoft.holoaccent.util.NativeResources;

public class DividerPainter {

	private static final String IDENTIFIER_NAME = "titleDivider";
	
	private final int mColor;
	
	public DividerPainter(int color) {
		mColor = color;
	}

	public DividerPainter(Context c) {
		TypedArray attrs = c.getTheme().obtainStyledAttributes(R.styleable.HoloAccent);
		int defaultColor = c.getResources().getColor(android.R.color.holo_blue_light);
		mColor = attrs.getColor(R.styleable.HoloAccent_accentColor, defaultColor);
		attrs.recycle();
	}
	
	public final void paint(Window window) {
        int id = NativeResources.getIdentifier(IDENTIFIER_NAME);
		View v = window.findViewById(id);
		if (v != null)
			v.setBackgroundColor(mColor);
	}
}
