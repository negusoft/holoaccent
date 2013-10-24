package com.negusoft.holoaccent.example.fragment;

import com.negusoft.holoaccent.example.R;
import com.negusoft.holoaccent.example.R.array;
import com.negusoft.holoaccent.example.R.id;
import com.negusoft.holoaccent.example.R.layout;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ListFragment extends Fragment {
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View result = inflater.inflate(R.layout.list, null);
		
		ListView listView = (ListView)result.findViewById(R.id.listView);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), 
				android.R.layout.simple_list_item_1, 
				getResources().getStringArray(R.array.list_items));
		listView.setAdapter(adapter);
		
		return result;
	}

}
