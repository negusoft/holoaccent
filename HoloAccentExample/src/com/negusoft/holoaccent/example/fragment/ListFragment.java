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
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.negusoft.holoaccent.example.R;

public class ListFragment extends Fragment implements OnItemLongClickListener {
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View result = inflater.inflate(R.layout.list, null);
		
		ListView listView = (ListView)result.findViewById(R.id.listView);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), 
				android.R.layout.simple_list_item_1, 
				getResources().getStringArray(R.array.list_items));
		listView.setAdapter(adapter);
		listView.setOnItemLongClickListener(this);
		listView.setFastScrollEnabled(true);
		listView.setFastScrollAlwaysVisible(true);
		
		return result;
	}

	@Override
	public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		getActivity().startActionMode(mActionModeCallback);
		return false;
	}
	
	private ActionMode.Callback mActionModeCallback = new ActionMode.Callback() {

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

		}

		@Override
		public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
			return false;
		}
		
	};

}
