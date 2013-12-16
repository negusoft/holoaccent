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

/**
 * Represents the accent color and makes it easy to 
 * access derived versions of it: Like translucent or 
 * dark versions.
 */
public class AccentPalette {
	
	private static final float DARK_ACCENT_PERCENTAGE = 0.85f;
	
	public final int accentColor;
	public final int red;
	public final int green;
	public final int blue;
	
	public final int accentColorDark;
	public final int redDark;
	public final int greenDark;
	public final int blueDark;
	
	/**
	 * Create an instance with the specified color. The dark 
	 * variant will be derived from it.
	 */
	public AccentPalette(int color) {
		red = Color.red(color);
		green = Color.green(color);
		blue = Color.blue(color);
		accentColor = Color.rgb(red, green, blue);

		redDark = (int)(red * DARK_ACCENT_PERCENTAGE);
		greenDark = (int)(green * DARK_ACCENT_PERCENTAGE);
		blueDark = (int)(blue * DARK_ACCENT_PERCENTAGE);
		accentColorDark = Color.rgb(redDark, greenDark, blueDark);
	}

	/**
	 * Create an instance with the specified default and dark 
	 * accent colors explicitly specified.
	 */
	public AccentPalette(int color, int colorDark) {
		red = Color.red(color);
		green = Color.green(color);
		blue = Color.blue(color);
		accentColor = Color.rgb(red, green, blue);
		
		if (colorDark == 0) {
			redDark = (int)(red * DARK_ACCENT_PERCENTAGE);
			greenDark = (int)(green * DARK_ACCENT_PERCENTAGE);
			blueDark = (int)(blue * DARK_ACCENT_PERCENTAGE);
			accentColorDark = Color.rgb(redDark, greenDark, blueDark);
		}
		else {
			redDark = Color.red(colorDark);
			greenDark = Color.green(colorDark);
			blueDark = Color.blue(colorDark);
			accentColorDark = Color.rgb(redDark, greenDark, blueDark);
		}
	}

	/**
	 * Create an instance with the specified color components. The dark 
	 * variant will be derived from them.
	 */
	public AccentPalette(int r, int g, int b) {
		accentColor = Color.rgb(r, g, b);
		red = r;
		green = g;
		blue = b;

		redDark = Color.red((int)(red * DARK_ACCENT_PERCENTAGE));
		greenDark = Color.green((int)(green * DARK_ACCENT_PERCENTAGE));
		blueDark = Color.blue((int)(blue * DARK_ACCENT_PERCENTAGE));
		accentColorDark = Color.rgb(redDark, greenDark, blueDark);
	}
	
	/** @return The accent color. Same as 'accentColor'. */
	public int getAccentColor() {
		return accentColor;
	}

	/** 
	 * Get a translucent version of the accent color. 
	 * @param alpha The opacity of the color [0..255]
	 */
	public int getAccentColor(int alpha) {
		return Color.argb(alpha, red, green, blue);
	}

	/** @return The accent color. Same as 'accentColorDark'. */
	public int getDarkAccentColor() {
		return accentColorDark;
	}

	/** 
	 * Get a translucent version of the dark variant of the accent color. 
	 * @param alpha The opacity of the color [0..255]
	 */
	public int getDarkAccentColor(int alpha) {
		return Color.argb(alpha, redDark, greenDark, blueDark);
	}

}
