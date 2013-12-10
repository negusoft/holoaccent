package com.negusoft.holoaccent.interceptor;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;

import com.negusoft.holoaccent.AccentPalette;
import com.negusoft.holoaccent.AccentResources;
import com.negusoft.holoaccent.R;
import com.negusoft.holoaccent.drawable.SearchViewDrawable;

public class SearchViewTextFieldInterceptor implements AccentResources.Interceptor {
	
	public static final int DEFAULT_COLOR = 0x4Dffffff;
	public static final int DEFAULT_COLOR_LIGHT = 0x4D000000;

	@Override
	public Drawable getDrawable(Resources res, AccentPalette palette, int resId) {
		if (resId == R.drawable.ha__ab_searchview_textfield_focused)
			return new SearchViewDrawable(res.getDisplayMetrics(), palette.accentColor);
		if (resId == R.drawable.ha__ab_searchview_textfield_default)
			return new SearchViewDrawable(res.getDisplayMetrics(), DEFAULT_COLOR);
		if (resId == R.drawable.ha__ab_searchview_textfield_default_light)
			return new SearchViewDrawable(res.getDisplayMetrics(), DEFAULT_COLOR_LIGHT);
		return null;
	}

}
