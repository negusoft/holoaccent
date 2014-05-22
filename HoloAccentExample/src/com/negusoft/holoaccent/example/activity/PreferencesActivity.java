package com.negusoft.holoaccent.example.activity;

import android.os.Bundle;
import android.preference.PreferenceFragment;

import com.negusoft.holoaccent.activity.AccentActivity;
import com.negusoft.holoaccent.example.R;
import com.negusoft.holoaccent.example.model.ColorOverrideConfig;

public class PreferencesActivity extends AccentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
        // Display the fragment as the main content.
        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .replace(android.R.id.content, new GeneralPreferenceFragment())
                    .commit();
        }
	}

    @Override
    public int getOverrideAccentColor() {
        return ColorOverrideConfig.getColor();
    }

	public static class GeneralPreferenceFragment extends PreferenceFragment {
		@Override
		public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			addPreferencesFromResource(R.xml.pref_general);
		}
	}
}
