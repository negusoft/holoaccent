package com.negusoft.holoaccent.preference;

import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.Window;

import com.negusoft.holoaccent.dialog.DividerPainter;

public class EditTextPreference extends android.preference.EditTextPreference {

	private final DividerPainter mPainter;

	public EditTextPreference(Context context) {
		super(context);
		mPainter = new DividerPainter(context);
	}

	public EditTextPreference(Context context, AttributeSet attrs) {
		super(context, attrs);
		mPainter = new DividerPainter(context);
	}

	public EditTextPreference(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		mPainter = new DividerPainter(context);
	}
	
	@Override
	protected void showDialog(Bundle state) {
		super.showDialog(state);
		Window w = getDialog().getWindow();
		mPainter.paint(w);
	}
}
