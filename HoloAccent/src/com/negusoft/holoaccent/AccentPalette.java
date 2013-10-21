package com.negusoft.holoaccent;

import android.graphics.Color;

public class AccentPalette {
	
	public final int accentColor;
	public final int red;
	public final int green;
	public final int blue;
	
	public AccentPalette(int color) {
		accentColor = color;
		red = Color.red(color);
		green = Color.green(color);
		blue = Color.blue(color);
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

}
