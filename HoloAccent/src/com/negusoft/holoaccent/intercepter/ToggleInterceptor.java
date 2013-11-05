package com.negusoft.holoaccent.intercepter;

import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;

import com.negusoft.holoaccent.AccentPalette;
import com.negusoft.holoaccent.AccentResources;
import com.negusoft.holoaccent.R;
import com.negusoft.holoaccent.drawable.ToggleForegroundDrawable;

public class ToggleInterceptor implements AccentResources.Interceptor {

	private final int COLOR_ON_PRESSED = Color.rgb(255, 255, 255);
	private final int COLOR_OFF = Color.argb(128, 0, 0, 0);
	private final int COLOR_OFF_DISABLED = Color.argb(64, 0, 0, 0);

	@Override
	public Drawable getDrawable(Resources res, AccentPalette palette, int resId) {
		if (resId == R.drawable.btn_toggle_comp_on_foreground)
			return new ToggleForegroundDrawable(res, palette.accentColor);
		if (resId == R.drawable.btn_toggle_comp_on_foreground_pressed)
			return new ToggleForegroundDrawable(res, COLOR_ON_PRESSED);
		if (resId == R.drawable.btn_toggle_comp_on_foreground_disabled)
			return new ToggleForegroundDrawable(res, palette.getTranslucent(128));
		if (resId == R.drawable.btn_toggle_comp_off_foreground)
			return new ToggleForegroundDrawable(res, COLOR_OFF);
		if (resId == R.drawable.btn_toggle_comp_off_foreground_disabled)
			return new ToggleForegroundDrawable(res, COLOR_OFF_DISABLED);
		return null;
	}

}
