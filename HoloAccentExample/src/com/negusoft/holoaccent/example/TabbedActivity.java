package com.negusoft.holoaccent.example;

import java.util.ArrayList;
import java.util.List;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;

import com.negusoft.holoaccent.AccentHelper;
import com.negusoft.holoaccent.dialog.AccentAlertDialog;
import com.negusoft.holoaccent.example.fragment.ProgressFragment;
import com.negusoft.holoaccent.example.fragment.ButtonFragment;
import com.negusoft.holoaccent.example.fragment.ChoicesFragment;
import com.negusoft.holoaccent.example.fragment.ListFragment;
import com.negusoft.holoaccent.example.fragment.TextviewFragment;

public class TabbedActivity extends FragmentActivity implements ActionBar.TabListener {

	SectionsPagerAdapter mSectionsPagerAdapter;
	ViewPager mViewPager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tabbed);

		final ActionBar actionBar = getActionBar();
		if (actionBar != null) {
			actionBar.setHomeButtonEnabled(true);

			FragmentManager fragmentManager = getSupportFragmentManager();
			mSectionsPagerAdapter = new SectionsPagerAdapter(fragmentManager);
			mViewPager = (ViewPager) findViewById(R.id.pager);
			mViewPager.setAdapter(mSectionsPagerAdapter);
			
			configureTabs(actionBar, mViewPager);
		}
	}
	
	protected void configureTabs(final ActionBar actionBar, ViewPager viewPager) {
		
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		viewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
					@Override public void onPageSelected(int position) {
						actionBar.setSelectedNavigationItem(position);
					}
				});

		// For each of the sections in the app, add a tab to the action bar.
		for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++) {
			Tab newTab = actionBar.newTab()
					.setText(mSectionsPagerAdapter.getPageTitle(i))
					.setTabListener(this);
			actionBar.addTab(newTab);
		}
	}
	
	private final AccentHelper mAccentHelper = new AccentHelper();
	@Override
	public Resources getResources() {
		return mAccentHelper.getResources(this, super.getResources());
	}
	
	@Override
	public void setTheme(int resid) {
		super.setTheme(resid);
	}
	
	@Override
	public boolean onOptionsItemSelected(android.view.MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
            startActivity(new Intent(this, TabbedActivity.class));
            return true;
		case R.id.alert_dialog:
			showAlertDialog();
            return true;
		case R.id.dialog_activity:
            startActivity(new Intent(this, DialogActivity.class));
            return true;
		case R.id.tab_strip_activity:
            startActivity(new Intent(this, TabbedStripActivity.class));
            return true;
		case R.id.spinner_activity:
            startActivity(new Intent(this, SpinnerActivity.class));
            return true;
		case R.id.code_override_activity:
            startActivity(new Intent(this, CodeOverrideActivity.class));
            return true;
		case R.id.theme_override_activity:
            startActivity(new Intent(this, ThemeOverrideActivity.class));
            return true;
		case R.id.holo_theme_activity:
            startActivity(new Intent(this, TabbetHoloActivity.class));
            return true;
		case R.id.preferences_activity:
            startActivity(new Intent(this, PreferencesActivity.class));
            return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.tabbed, menu);
		return true;
	}

	@Override
	public void onTabSelected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
		// When the given tab is selected, switch to the corresponding page in
		// the ViewPager.
		mViewPager.setCurrentItem(tab.getPosition());
	}

	@Override
	public void onTabUnselected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
	}

	@Override
	public void onTabReselected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
	}
	
	private void showAlertDialog() {
		AccentAlertDialog.Builder builder = new AccentAlertDialog.Builder(this);
		builder.setTitle("Alert Dialog")
				.setMessage("Dummie dialog")
				.setPositiveButton("Positive",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								// positive action
							}
						})
				.setNegativeButton("Negative",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								// negative action
							}
						});
		builder.show();
	}
	
	private final class FragmentTabHolder {
		public final Fragment fragment;
		public final String title;
		public FragmentTabHolder(Fragment fragment, int titleResId) {
			this.fragment = fragment;
			this.title = getString(titleResId);
		}
	}

	public class SectionsPagerAdapter extends FragmentPagerAdapter {
		
		private final List<FragmentTabHolder> mFragments;

		public SectionsPagerAdapter(FragmentManager fm) {
			super(fm);
			mFragments = new ArrayList<FragmentTabHolder>();
			mFragments.add(new FragmentTabHolder(new ChoicesFragment(), R.string.tab_choices));
			mFragments.add(new FragmentTabHolder(new ButtonFragment(), R.string.tab_buttons));
			mFragments.add(new FragmentTabHolder(new ListFragment(), R.string.tab_list));
			mFragments.add(new FragmentTabHolder(new TextviewFragment(), R.string.tab_text_fields));
			mFragments.add(new FragmentTabHolder(new ProgressFragment(), R.string.tab_progress));
		}

		@Override
		public Fragment getItem(int position) {
			return mFragments.get(position).fragment;
		}

		@Override
		public int getCount() {
			return mFragments.size();
		}

		@Override
		public CharSequence getPageTitle(int position) {
			return mFragments.get(position).title;
		}
	}

}
