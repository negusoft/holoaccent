package com.negusoft.holoaccent.intercepter;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;

import com.negusoft.holoaccent.AccentPalette;
import com.negusoft.holoaccent.AccentResources;
import com.negusoft.holoaccent.R;
import com.negusoft.holoaccent.drawable.RectDrawable;

public class RectInterceptor implements AccentResources.Interceptor {
	
	@Override
	public Drawable getDrawable(Resources res, AccentPalette palette, int resId) {
		if (resId == R.drawable.rect_focused_background) {
			int backColor = palette.getTranslucent(0x55);
			int borderColor = palette.getTranslucent(0xAA);
			return new RectDrawable(res, backColor, 2f, borderColor);
		}
		return null;
	}

}
