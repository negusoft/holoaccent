package com.negusoft.holoaccent.example;

import com.negusoft.holoaccent.ResourceHelper;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.res.Resources;
import android.content.res.Resources.Theme;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		getActionBar().setHomeButtonEnabled(true);
		
		ActionBar actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
		actionBar.setDisplayShowTitleEnabled(false);
		
		String[] items = new String[] { "Item 1", "Item 2", "Item 3", "Item 4" };
		ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);
		actionBar.setListNavigationCallbacks(arrayAdapter, new ActionBar.OnNavigationListener() {
			@Override public boolean onNavigationItemSelected(int itemPosition, long itemId) {
				return false;
			}
		});
//		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
//		Tab tab = actionBar.newTab()
//                .setText("Tab 1")
//                .setTabListener(new TabListener());
//		actionBar.addTab(tab);
//
//		tab = actionBar.newTab()
//            .setText("Tab 2")
//            .setTabListener(new TabListener());
//		actionBar.addTab(tab);
//
//		tab = actionBar.newTab()
//            .setText("Tab 3")
//            .setTabListener(new TabListener());
//		actionBar.addTab(tab);
//		
//		ListView listView = (ListView)findViewById(R.id.listView);
//		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, 
//				android.R.layout.simple_list_item_1, 
//				getResources().getStringArray(R.array.list_items));
//		listView.setAdapter(adapter);
		
	}
	
	private final ResourceHelper mResourceHelper = new ResourceHelper();
	@Override
	public Resources getResources() {
		return mResourceHelper.getResources(this, super.getResources());
	}
	
	@Override
	public Theme getTheme() {
		return super.getTheme();
	}
	
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	public static class TabListener implements ActionBar.TabListener {

	    public void onTabSelected(Tab tab, FragmentTransaction ft) {
	    }

	    public void onTabUnselected(Tab tab, FragmentTransaction ft) {
	    	
	    }

	    public void onTabReselected(Tab tab, FragmentTransaction ft) {
	    	
	    }
	}

}
