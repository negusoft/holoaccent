package com.negusoft.holoaccent.example.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView.MultiChoiceModeListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;

import com.negusoft.holoaccent.example.R;

public class ListFragment extends Fragment implements OnItemClickListener {
	
	private ListView mListView;
	private ArrayAdapter<String> mAdapter;
	private ActionMode mActionMode;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View result = inflater.inflate(R.layout.list, null);
		
		mListView = (ListView)result.findViewById(R.id.listView);
		mAdapter = new ArrayAdapter<String>(getActivity(), 
				R.layout.list_item_multiple_choice,
				android.R.id.text1,
				getResources().getStringArray(R.array.list_items));
		mListView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
		mListView.setAdapter(mAdapter);
		mListView.setOnItemClickListener(this);
		mListView.setMultiChoiceModeListener(mMultiChoiceModeListener);
		mListView.setFastScrollEnabled(true);
		mListView.setFastScrollAlwaysVisible(true);
		
		setHasOptionsMenu(true);
		
		return result;
	}
	
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater.inflate(R.menu.list, menu);
		SearchView searchView = (SearchView)menu.findItem(R.id.search).getActionView();
		searchView.setOnQueryTextListener(mOnQueryTextListener);
		searchView.setOnCloseListener(mOnCloseListener);
		
		super.onCreateOptionsMenu(menu, inflater);
	}
	
	private final SearchView.OnQueryTextListener mOnQueryTextListener = new SearchView.OnQueryTextListener() {
		@Override public boolean onQueryTextChange(String newText) {
			mAdapter.getFilter().filter(newText);
			return true;
		}
		@Override public boolean onQueryTextSubmit(String query) {
			mAdapter.getFilter().filter(query);
			return true;
		}
	};

	private final SearchView.OnCloseListener mOnCloseListener = new SearchView.OnCloseListener() {
		@Override public boolean onClose() {
			mAdapter.getFilter().filter(null);
			return false;
		}
	};

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		if (mActionMode != null) {
			if (mListView.getCheckedItemCount() == 0)
				mActionMode.finish();
		} else {
			getActivity().startActionMode(mMultiChoiceModeListener);
		}
	}
	
	private MultiChoiceModeListener mMultiChoiceModeListener = new MultiChoiceModeListener() {

		@Override
		public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
			return true;
		}

		@Override
		public boolean onCreateActionMode(ActionMode mode, Menu menu) {
	        MenuInflater inflater = mode.getMenuInflater();
	        inflater.inflate(R.menu.spinner, menu);
			return true;
		}

		@Override
		public void onDestroyActionMode(ActionMode mode) {
			mActionMode = null;
			for (int pos = 0; pos < mListView.getCount(); pos++)
				mListView.setItemChecked(pos, false);
		}

		@Override
		public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
			mActionMode = mode;
			return false;
		}

		@Override
		public void onItemCheckedStateChanged(ActionMode mode, int position, long id, boolean checked) {

		}};

}
