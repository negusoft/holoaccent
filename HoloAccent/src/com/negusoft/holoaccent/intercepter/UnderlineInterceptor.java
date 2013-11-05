package com.negusoft.holoaccent.intercepter;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;

import com.negusoft.holoaccent.AccentPalette;
import com.negusoft.holoaccent.AccentResources;
import com.negusoft.holoaccent.R;
import com.negusoft.holoaccent.drawable.UnderlineDrawable;

public class UnderlineInterceptor implements AccentResources.Interceptor {

	@Override
	public Drawable getDrawable(Resources res, AccentPalette palette, int resId) {
		if (resId == R.drawable.underline_1_5)
			return new UnderlineDrawable(res, palette.accentColor, 1.5f);
		if (resId == R.drawable.underline_3)
			return new UnderlineDrawable(res, palette.accentColor, 3f);
		if (resId == R.drawable.underline_6)
			return new UnderlineDrawable(res, palette.accentColor, 6f);
		// overline
		if (resId == R.drawable.overline_3)
			return new UnderlineDrawable(res, palette.accentColor, 3f, true);
		return null;
	}

}
