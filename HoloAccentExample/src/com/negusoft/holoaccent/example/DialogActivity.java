package com.negusoft.holoaccent.example;

import com.negusoft.holoaccent.ResourceHelper;
import com.negusoft.holoaccent.dialog.DividerPainter;

import android.app.Activity;
import android.content.res.Resources;
import android.os.Bundle;

public class DialogActivity extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		new DividerPainter(this).paint(getWindow());
	}
	
	private final ResourceHelper mResourceHelper = new ResourceHelper();
	@Override
	public Resources getResources() {
		return mResourceHelper.getResources(this, super.getResources());
	}

}
