package com.negusoft.holoaccent.example.activity;

import android.app.ActionBar;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.negusoft.holoaccent.example.R;

public class TabbedStripActivity extends TabbedActivity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		findViewById(R.id.tabStrip).setVisibility(View.VISIBLE);
	}
	
	@Override
	protected int getLayoutId() {
		return R.layout.activity_tab_strip;
	}
	
	@Override
	protected void configureTabs(ActionBar actionBar, ViewPager viewPager) {
		// EMPTY: don't configure the tabs not to show them
	}

}
