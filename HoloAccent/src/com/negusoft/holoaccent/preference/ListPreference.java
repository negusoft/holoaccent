package com.negusoft.holoaccent.preference;

import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.Window;

import com.negusoft.holoaccent.dialog.DividerPainter;

public class ListPreference extends android.preference.ListPreference {

	private final DividerPainter mPainter;

	public ListPreference(Context context) {
		super(context);
		mPainter = new DividerPainter(context);
	}

	public ListPreference(Context context, AttributeSet attrs) {
		super(context, attrs);
		mPainter = new DividerPainter(context);
	}
	
	@Override
	protected void showDialog(Bundle state) {
		super.showDialog(state);
		Window w = getDialog().getWindow();
		mPainter.paint(w);
	}
}
