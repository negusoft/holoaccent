package com.negusoft.holoaccent.example.activity;

import android.app.ActionBar;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.negusoft.holoaccent.activity.AccentActivity;
import com.negusoft.holoaccent.example.R;
import com.negusoft.holoaccent.example.fragment.SimpleDialogFragment;
import com.negusoft.holoaccent.example.model.ColorOverrideConfig;

public class SpinnerActivity extends AccentActivity {

	private static final String[] ITEMS = new String[] { "Item 1", "Item 2", "Item 3", "Item 4" };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_spinner);
		
		initActionBar();
		initSpinners();
	}

    @Override
    public int getOverrideAccentColor() {
        return ColorOverrideConfig.getColor();
    }

    @Override
    public boolean onOptionsItemSelected(android.view.MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

	private void initActionBar() {
		ActionBar actionBar = getActionBar();
		if (actionBar == null)
			return;
		
		actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
		
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
		actionBar.setDisplayShowTitleEnabled(false);
		
		actionBar.setListNavigationCallbacks(new ActionBarArrayAdapter(), new ActionBar.OnNavigationListener() {
			@Override public boolean onNavigationItemSelected(int itemPosition, long itemId) {
				return false;
			}
		});
	}
	
	private void initSpinners() {
		Spinner spinner1 = (Spinner)findViewById(R.id.spinner1);
		spinner1.setAdapter(new DefaultArrayAdapter());
		Spinner spinner2 = (Spinner)findViewById(R.id.spinner2);
		spinner2.setAdapter(new DefaultArrayAdapter());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.spinner, menu);
		return true;
	}

    private class ActionBarArrayAdapter extends ArrayAdapter<String> {
        public ActionBarArrayAdapter() {
            super(SpinnerActivity.this, R.layout.spinner_dropdown, ITEMS);
        }
    }

    private class DefaultArrayAdapter extends ArrayAdapter<String> {
        public DefaultArrayAdapter() {
            super(SpinnerActivity.this, android.R.layout.simple_spinner_dropdown_item, ITEMS);
        }
    }

}
