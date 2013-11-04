package com.negusoft.holoaccent.example;

import android.content.res.Resources;
import android.os.Bundle;

import com.negusoft.holoaccent.ResourceHelper;
import com.negusoft.holoaccent.dialog.DividerPainter;

public class DialogActivity extends SpinnerActivity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		new DividerPainter(this).paint(getWindow());
	}
	
	private final ResourceHelper mResourceHelper = new ResourceHelper();
	@Override
	public Resources getResources() {
		return mResourceHelper.getResources(this, super.getResources());
	}

}
