package com.negusoft.holoaccent.intercepter;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;

import com.negusoft.holoaccent.AccentPalette;
import com.negusoft.holoaccent.AccentResources;
import com.negusoft.holoaccent.R;
import com.negusoft.holoaccent.drawable.FastScrollDrawable;

public class FastScrollInterceptor implements AccentResources.Interceptor {
	
	@Override
	public Drawable getDrawable(Resources res, AccentPalette palette, int resId) {
		if (resId == R.drawable.fastscroll_thumb_default)
			return new FastScrollDrawable(res.getDisplayMetrics(), palette, false);
		if (resId == R.drawable.fastscroll_thumb_pressed)
			return new FastScrollDrawable(res.getDisplayMetrics(), palette, true);
		return null;
	}

}
