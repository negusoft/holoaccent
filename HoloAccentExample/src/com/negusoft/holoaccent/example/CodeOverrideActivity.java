package com.negusoft.holoaccent.example;

import android.content.res.Resources;
import android.graphics.Color;

import com.negusoft.holoaccent.AccentHelper;

public class CodeOverrideActivity extends TabbedActivity {

    @Override
    protected int getOverrideAccentColor() {
        return Color.RED;
    }

}
