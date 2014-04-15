package com.negusoft.holoaccent.interceptor;

import android.content.res.Resources;

import com.negusoft.holoaccent.AccentPalette;
import com.negusoft.holoaccent.AccentResources;
import com.negusoft.holoaccent.R;

public class AccentColorInterceptor implements AccentResources.ColorInterceptor {

    public int getColor(Resources res, AccentPalette palette, int resId) {
        if (resId == R.color.ha__accent_reference)
            return palette.getAccentColor();
        if (resId == R.color.ha__accent_dark_reference)
            return palette.getDarkAccentColor();
        if (resId == R.color.ha__accent_translucent_reference)
            return palette.getAccentColor(0x66);
        if (resId == R.color.ha__calendar_selected_week_reference)
            return palette.getAccentColor(0x33);
        if (resId == R.color.ha__picker_divider_reference)
            return palette.getAccentColor(0xCC);
        return 0;
    }

}
