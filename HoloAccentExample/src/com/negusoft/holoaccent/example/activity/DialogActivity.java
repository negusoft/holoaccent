package com.negusoft.holoaccent.example.activity;

import android.os.Bundle;

import com.negusoft.holoaccent.example.model.ColorOverrideConfig;

public class DialogActivity extends SpinnerActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        getAccentHelper().prepareDialog(this, getWindow());
	}

    @Override
    public int getOverrideAccentColor() {
        return ColorOverrideConfig.getColor();
    }

}
