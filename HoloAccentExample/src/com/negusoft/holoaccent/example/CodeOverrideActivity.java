package com.negusoft.holoaccent.example;

import android.graphics.Color;

public class CodeOverrideActivity extends TabbedActivity {

    @Override
    protected Integer overrideAccentColor() {
        return Color.RED;
    }
}
