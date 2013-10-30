package com.negusoft.holoaccent.example.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import com.negusoft.holoaccent.example.R;

public class TextviewFragment extends Fragment {
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.textfields, null);
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		String[] items = getResources().getStringArray(R.array.list_items);
		
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_dropdown_item_1line, items);
		AutoCompleteTextView autoCompleteTextView1 = 
				(AutoCompleteTextView)getView().findViewById(R.id.autoCompleteTextView1);
		autoCompleteTextView1.setAdapter(adapter1);

        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_dropdown_item_1line, items);
		AutoCompleteTextView autoCompleteTextView2 = 
				(AutoCompleteTextView)getView().findViewById(R.id.autoCompleteTextView2);
		autoCompleteTextView2.setAdapter(adapter2);
	}

}
