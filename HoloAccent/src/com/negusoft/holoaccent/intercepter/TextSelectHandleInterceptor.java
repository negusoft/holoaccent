package com.negusoft.holoaccent.intercepter;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;

import com.negusoft.holoaccent.AccentPalette;
import com.negusoft.holoaccent.AccentResources;
import com.negusoft.holoaccent.R;
import com.negusoft.holoaccent.drawable.TextSelectHandleDrawable;
import com.negusoft.holoaccent.drawable.TextSelectHandleDrawable.HandleType;

public class TextSelectHandleInterceptor implements AccentResources.Interceptor {
	
	@Override
	public Drawable getDrawable(Resources res, AccentPalette palette, int resId) {
		if (resId == R.drawable.text_select_handle_left_accent)
			return new TextSelectHandleDrawable(res.getDisplayMetrics(), palette, HandleType.LEFT);
		if (resId == R.drawable.text_select_handle_right_accent)
			return new TextSelectHandleDrawable(res.getDisplayMetrics(), palette, HandleType.RIGHT);
		if (resId == R.drawable.text_select_handle_middle_accent)
			return new TextSelectHandleDrawable(res.getDisplayMetrics(), palette, HandleType.MIDDLE);
		return null;
	}

}
