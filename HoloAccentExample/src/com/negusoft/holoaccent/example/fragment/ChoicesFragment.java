package com.negusoft.holoaccent.example.fragment;

import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.NumberPicker;

import com.negusoft.holoaccent.example.R;

public class ChoicesFragment extends Fragment {
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View result = inflater.inflate(R.layout.choices, null);

        NumberPicker nPicker1 = (NumberPicker)result.findViewById(R.id.numberPicker);
        nPicker1.setMinValue(0);
        nPicker1.setMaxValue(100);
        nPicker1.setValue(30);

        return result;
	}

}
