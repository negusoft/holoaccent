package com.negusoft.holoaccent.example;

import android.content.res.Resources;
import android.graphics.Color;

import com.negusoft.holoaccent.AccentHelper;

public class CodeOverrideActivity extends TabbedActivity {
	
	private final AccentHelper mAccentHelper = new AccentHelper(Color.RED);
	@Override
	public Resources getResources() {
		return mAccentHelper.getResources(this, super.getResources());
	}

}
