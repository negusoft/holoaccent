package com.negusoft.holoaccent.activity;

import android.app.Activity;
import android.content.res.Resources;

import com.negusoft.holoaccent.AccentHelper;

/**
 * Extends the Activity class and adds the HoloAccent configuration.
 */
public abstract class AccentActivity extends Activity {

    private final AccentHelper mAccentHelper = new AccentHelper(getOverrideAccentColor());

    @Override
    public Resources getResources() {
        return mAccentHelper.getResources(this, super.getResources());
    }

    /**
     * Override this method to set the accent color programmatically.
     * @return The color to override. If the color is equals to 0, the
     * accent color will be taken from the theme.
     */
    protected int getOverrideAccentColor() {
        return 0;
    }

    protected AccentHelper getAccentHelper() {
        return mAccentHelper;
    }

}
