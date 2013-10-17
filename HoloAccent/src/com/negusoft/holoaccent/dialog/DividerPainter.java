package com.negusoft.holoaccent.dialog;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.view.View;
import android.view.Window;

import com.negusoft.holoaccent.R;

public class DividerPainter {

	private static final String RESOURCE_TYPE = "id";
	private static final String RESOURCE_PACKAGE = "android";
	private static final String RESOURCE_NAME = "titleDivider";
	
	private final int mColor;
	
	public DividerPainter(int color) {
		mColor = color;
	}

	public DividerPainter(Context c) {
		TypedArray attrs = c.getTheme().obtainStyledAttributes(R.styleable.HoloAccent);
		int defaultColor = c.getResources().getColor(android.R.color.holo_blue_light);
		mColor = attrs.getColor(R.styleable.HoloAccent_accentColor, defaultColor);
		attrs.recycle();
	}
	
	public final void paint(Window window) {
		Resources res = Resources.getSystem();
		int id = res.getIdentifier(RESOURCE_NAME, RESOURCE_TYPE, RESOURCE_PACKAGE);
		View v = window.findViewById(id);
		if (v != null)
			v.setBackgroundColor(mColor);
	}
}
