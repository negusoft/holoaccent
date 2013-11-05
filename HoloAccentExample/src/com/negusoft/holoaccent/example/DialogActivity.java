package com.negusoft.holoaccent.example;

import android.content.res.Resources;
import android.os.Bundle;

import com.negusoft.holoaccent.AccentHelper;

public class DialogActivity extends SpinnerActivity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mAccentHelper.prepareDialog(this, getWindow());
	}
	
	private final AccentHelper mAccentHelper = new AccentHelper();
	@Override
	public Resources getResources() {
		return mAccentHelper.getResources(this, super.getResources());
	}

}
