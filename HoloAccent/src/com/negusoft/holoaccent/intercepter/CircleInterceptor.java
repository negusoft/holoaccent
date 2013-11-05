package com.negusoft.holoaccent.intercepter;

import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;

import com.negusoft.holoaccent.AccentPalette;
import com.negusoft.holoaccent.AccentResources;
import com.negusoft.holoaccent.R;
import com.negusoft.holoaccent.drawable.CircleDrawable;

public class CircleInterceptor implements AccentResources.Interceptor {
	
	@Override
	public Drawable getDrawable(Resources res, AccentPalette palette, int resId) {
		if (resId == R.drawable.circle_pressed) {
			int backColor = palette.getTranslucent(0x88);
			return new CircleDrawable(res, 16f, backColor, 0f, Color.TRANSPARENT);
		}
		if (resId == R.drawable.circle_focused) {
			int borderColor = palette.getTranslucent(0xAA);
			return new CircleDrawable(res, 11f, Color.TRANSPARENT, 1.5f, borderColor);
		}
		if (resId == R.drawable.circle_disabled_focused) {
			int borderColor = palette.getTranslucent(0x55);
			return new CircleDrawable(res, 11f, Color.TRANSPARENT, 1.5f, borderColor);
		}
		return null;
	}

}
