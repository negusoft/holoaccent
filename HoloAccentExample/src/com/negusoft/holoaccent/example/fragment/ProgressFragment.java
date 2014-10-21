package com.negusoft.holoaccent.example.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.negusoft.holoaccent.example.R;

public class ProgressFragment extends Fragment {
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.progress, null);
	}
	
	@Override
	public void onStart() {
		super.onStart();
		getActivity().findViewById(R.id.myseekbar).setEnabled(false);
	}

}
