package com.negusoft.holoaccent.example;

import android.content.res.Resources;
import android.graphics.Color;

import com.negusoft.holoaccent.ResourceHelper;

public class CodeOverrideActivity extends TabbedActivity {
	
	private final ResourceHelper mResourceHelper = new ResourceHelper(Color.RED);
	@Override
	public Resources getResources() {
		return mResourceHelper.getResources(this, super.getResources());
	}

}
