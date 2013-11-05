package com.negusoft.holoaccent.intercepter;

import android.content.res.Resources;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;

import com.negusoft.holoaccent.AccentPalette;
import com.negusoft.holoaccent.AccentResources;
import com.negusoft.holoaccent.R;

public class SolidColorInterceptor implements AccentResources.Interceptor {

	private static final int PRESSED_ALPHA = 0xAA;
	private static final int FOCUSED_ALPHA = 0x55;
	private static final int FOCUSED_DIMMED_ALPHA = 0x22;

	@Override
	public Drawable getDrawable(Resources res, AccentPalette palette, int resId) {
		if (resId == R.drawable.solid_pressed)
			return new ColorDrawable(palette.getTranslucent(PRESSED_ALPHA));
		if (resId == R.drawable.solid_focused)
			return new ColorDrawable(palette.getTranslucent(FOCUSED_ALPHA));
		if (resId == R.drawable.solid_focused_dimmed)
			return new ColorDrawable(palette.getTranslucent(FOCUSED_DIMMED_ALPHA));
		return null;
	}

}
