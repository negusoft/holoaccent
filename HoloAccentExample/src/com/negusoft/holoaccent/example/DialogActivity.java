package com.negusoft.holoaccent.example;

import android.os.Bundle;

public class DialogActivity extends SpinnerActivity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mAccentHelper.prepareDialog(this, getWindow());
	}

}
