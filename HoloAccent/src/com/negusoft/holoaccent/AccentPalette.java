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
package com.negusoft.holoaccent;

import android.graphics.Color;

public class AccentPalette {
	
	private static final float DARK_ACCENT_PERCENTAGE = 0.85f;
	
	public final int accentColor;
	public final int red;
	public final int green;
	public final int blue;
	
	public AccentPalette(int color) {
		red = Color.red(color);
		green = Color.green(color);
		blue = Color.blue(color);
		accentColor = Color.rgb(red, green, blue);
	}
	
	public AccentPalette(int r, int g, int b) {
		accentColor = Color.rgb(r, g, b);
		red = r;
		green = g;
		blue = b;
	}
	
	public int getTranslucent(int alpha) {
		return Color.argb(alpha, red, green, blue);
	}
	
	public int getDarkAccentColor() {
		return getDarkAccentColor(255);
	}
	
	public int getDarkAccentColor(int alpha) {
		int r = (int)(red * DARK_ACCENT_PERCENTAGE);
		int g = (int)(green * DARK_ACCENT_PERCENTAGE);
		int b = (int)(blue * DARK_ACCENT_PERCENTAGE);
		return Color.argb(alpha, r, g, b);
	}

}
