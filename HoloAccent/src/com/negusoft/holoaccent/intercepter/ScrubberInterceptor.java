package com.negusoft.holoaccent.intercepter;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;

import com.negusoft.holoaccent.AccentPalette;
import com.negusoft.holoaccent.AccentResources;
import com.negusoft.holoaccent.R;
import com.negusoft.holoaccent.drawable.ScrubberControlSelectorDrawable;
import com.negusoft.holoaccent.drawable.ScrubberControlSelectorDrawable.SelectorType;
import com.negusoft.holoaccent.drawable.ScrubberProgressDrawable;

public class ScrubberInterceptor implements AccentResources.Interceptor {
	
	@Override
	public Drawable getDrawable(Resources res, AccentPalette palette, int resId) {
		// control selector
		if (resId == R.drawable.scrubber_control_disabled)
			return new ScrubberControlSelectorDrawable(res.getDisplayMetrics(), palette, SelectorType.DISABLED);
		if (resId == R.drawable.scrubber_control_focused)
			return new ScrubberControlSelectorDrawable(res.getDisplayMetrics(), palette, SelectorType.FOCUSED);
		if (resId == R.drawable.scrubber_control_normal)
			return new ScrubberControlSelectorDrawable(res.getDisplayMetrics(), palette, SelectorType.NORMAL);
		if (resId == R.drawable.scrubber_control_pressed)
			return new ScrubberControlSelectorDrawable(res.getDisplayMetrics(), palette, SelectorType.PRESSED);
		
		// progress indicators
		if (resId == R.drawable.scrubber_comp_primary)
			return new ScrubberProgressDrawable(res.getDisplayMetrics(), palette);
		if (resId == R.drawable.scrubber_comp_secondary)
			return new ScrubberProgressDrawable(res.getDisplayMetrics(), palette, 77);
		return null;
	}

}
