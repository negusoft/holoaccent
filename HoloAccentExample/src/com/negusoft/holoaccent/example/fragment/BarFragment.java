package com.negusoft.holoaccent.example.fragment;

import com.negusoft.holoaccent.example.R;
import com.negusoft.holoaccent.example.R.layout;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class BarFragment extends Fragment {
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.bars, null);
	}

}
