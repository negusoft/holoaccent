package com.negusoft.holoaccent.example.preference;

import android.content.Context;
import android.util.AttributeSet;

import com.negusoft.holoaccent.example.R;
import com.negusoft.holoaccent.preference.DialogPreference;

public class CustomDialogPreference extends DialogPreference {

	public CustomDialogPreference(Context context, AttributeSet attrs) {
		super(context, attrs);
		setDialogMessage(R.string.pref_message_dialog);
	}

	public CustomDialogPreference(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		setDialogMessage(R.string.pref_message_dialog);
	}

}
