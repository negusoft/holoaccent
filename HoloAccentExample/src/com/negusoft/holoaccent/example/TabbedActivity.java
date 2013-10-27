package com.negusoft.holoaccent.example;

import java.util.ArrayList;
import java.util.List;

import android.app.ActionBar;
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
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.negusoft.holoaccent.ResourceHelper;
import com.negusoft.holoaccent.dialog.AccentAlertDialog;
import com.negusoft.holoaccent.example.fragment.BarFragment;
import com.negusoft.holoaccent.example.fragment.ButtonFragment;
import com.negusoft.holoaccent.example.fragment.CheckFragment;
import com.negusoft.holoaccent.example.fragment.ListFragment;
import com.negusoft.holoaccent.example.fragment.RadioFragment;
import com.negusoft.holoaccent.example.fragment.TextviewFragment;

public class TabbedActivity extends FragmentActivity implements ActionBar.TabListener {

	/**
	 * The {@link android.support.v4.view.PagerAdapter} that will provide
	 * fragments for each of the sections. We use a
	 * {@link android.support.v4.app.FragmentPagerAdapter} derivative, which
	 * will keep every loaded fragment in memory. If this becomes too memory
	 * intensive, it may be best to switch to a
	 * {@link android.support.v4.app.FragmentStatePagerAdapter}.
	 */
	SectionsPagerAdapter mSectionsPagerAdapter;

	/**
	 * The {@link ViewPager} that will host the section contents.
	 */
	ViewPager mViewPager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tabbed);

		final ActionBar actionBar = getActionBar();
		if (actionBar != null) {
			actionBar.setHomeButtonEnabled(true);
			
			actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

			// Create the adapter that will return a fragment for each of the three
			// primary sections of the app.
			mSectionsPagerAdapter = new SectionsPagerAdapter(
					getSupportFragmentManager());

			// Set up the ViewPager with the sections adapter.
			mViewPager = (ViewPager) findViewById(R.id.pager);
			mViewPager.setAdapter(mSectionsPagerAdapter);

			// When swiping between different sections, select the corresponding
			// tab. We can also use ActionBar.Tab#select() to do this if we have
			// a reference to the Tab.
			mViewPager
					.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
						@Override
						public void onPageSelected(int position) {
							actionBar.setSelectedNavigationItem(position);
						}
					});

			// For each of the sections in the app, add a tab to the action bar.
			for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++) {
				// Create a tab with text corresponding to the page title defined by
				// the adapter. Also specify this Activity object, which implements
				// the TabListener interface, as the callback (listener) for when
				// this tab is selected.
				actionBar.addTab(actionBar.newTab()
						.setText(mSectionsPagerAdapter.getPageTitle(i))
						.setTabListener(this));
			}
		}
	}
	
	private final ResourceHelper mResourceHelper = new ResourceHelper();
	@Override
	public Resources getResources() {
		return mResourceHelper.getResources(this, super.getResources());
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

//        AlertDialog d = builder.create();
//        d.show();
		builder.show();
	}

	/**
	 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
	 * one of the sections/tabs/pages.
	 */
	public class SectionsPagerAdapter extends FragmentPagerAdapter {
		
		private final List<Fragment> mFragments;

		public SectionsPagerAdapter(FragmentManager fm) {
			super(fm);
			mFragments = new ArrayList<Fragment>();
			mFragments.add(new CheckFragment());
			mFragments.add(new BarFragment());
			mFragments.add(new TextviewFragment());
			mFragments.add(new ListFragment());
			mFragments.add(new RadioFragment());
			mFragments.add(new ButtonFragment());
		}

		@Override
		public Fragment getItem(int position) {
			return mFragments.get(position);
		}

		@Override
		public int getCount() {
			return mFragments.size();
		}

		@Override
		public CharSequence getPageTitle(int position) {
			return "Section " + position;
		}
	}

	/**
	 * A dummy fragment representing a section of the app, but that simply
	 * displays dummy text.
	 */
	public static class DummySectionFragment extends Fragment {
		/**
		 * The fragment argument representing the section number for this
		 * fragment.
		 */
		public static final String ARG_SECTION_NUMBER = "section_number";

		public DummySectionFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_tabbed_dummy,
					container, false);
			TextView dummyTextView = (TextView) rootView
					.findViewById(R.id.section_label);
			dummyTextView.setText(Integer.toString(getArguments().getInt(
					ARG_SECTION_NUMBER)));
			return rootView;
		}
	}

}
