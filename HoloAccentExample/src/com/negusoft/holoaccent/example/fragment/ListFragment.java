package com.negusoft.holoaccent.example.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
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

import com.negusoft.holoaccent.example.R;

public class ListFragment extends Fragment implements OnItemClickListener {
	
	private ListView mListView;
	private ActionMode mActionMode;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View result = inflater.inflate(R.layout.list, null);
		
		mListView = (ListView)result.findViewById(R.id.listView);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), 
				R.layout.list_item_multiple_choice,
				android.R.id.text1,
				getResources().getStringArray(R.array.list_items));
		mListView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
		mListView.setAdapter(adapter);
		mListView.setOnItemClickListener(this);
		mListView.setMultiChoiceModeListener(mMultiChoiceModeListener);
		mListView.setFastScrollEnabled(true);
		mListView.setFastScrollAlwaysVisible(true);
		
		return result;
	}

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
