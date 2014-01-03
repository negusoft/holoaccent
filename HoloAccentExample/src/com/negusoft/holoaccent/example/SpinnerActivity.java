package com.negusoft.holoaccent.example;

import android.app.ActionBar;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.negusoft.holoaccent.activity.AccentActivity;

public class SpinnerActivity extends AccentActivity {

	private static final String[] ITEMS = new String[] { "Item 1", "Item 2", "Item 3", "Item 4" };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_spinner);
		
		initActionBar();
		initSpinners();
	}
	
	private void initActionBar() {
		ActionBar actionBar = getActionBar();
		if (actionBar == null)
			return;
		
		actionBar.setHomeButtonEnabled(true);
		
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
		actionBar.setDisplayShowTitleEnabled(false);
		
		actionBar.setListNavigationCallbacks(new MyArrayAdapter(), new ActionBar.OnNavigationListener() {
			@Override public boolean onNavigationItemSelected(int itemPosition, long itemId) {
				return false;
			}
		});
	}
	
	private void initSpinners() {
		Spinner spinner1 = (Spinner)findViewById(R.id.spinner1);
		spinner1.setAdapter(new MyArrayAdapter());
		Spinner spinner2 = (Spinner)findViewById(R.id.spinner2);
		spinner2.setAdapter(new MyArrayAdapter());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.spinner, menu);
		return true;
	}
	
	private class MyArrayAdapter extends ArrayAdapter<String> {
		public MyArrayAdapter() {
			super(SpinnerActivity.this, android.R.layout.simple_spinner_dropdown_item, ITEMS);
		}
	}

}
