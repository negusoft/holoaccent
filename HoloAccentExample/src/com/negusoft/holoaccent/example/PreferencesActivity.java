package com.negusoft.holoaccent.example;

import android.os.Bundle;
import android.preference.PreferenceFragment;

import com.negusoft.holoaccent.activity.AccentActivity;

public class PreferencesActivity extends AccentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
        // Display the fragment as the main content.
        getFragmentManager().beginTransaction()
                .replace(android.R.id.content, new GeneralPreferenceFragment())
                .commit();
	}

	public static class GeneralPreferenceFragment extends PreferenceFragment {
		@Override
		public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			addPreferencesFromResource(R.xml.pref_general);
		}
	}
}
