package com.negusoft.holoaccent.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.widget.SearchView;

import com.negusoft.holoaccent.R;
import com.negusoft.holoaccent.util.NativeResources;

public class AccentSearchView extends SearchView {
	
	private static final int SET_DRAWABLE_MIN_SDK = 16;

	private static final String IDENTIFIER_NAME = "search_plate";

	public AccentSearchView(Context context) {
		super(context);
		initBackground(context, null);
	}

	public AccentSearchView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initBackground(context, attrs);
	}

	private void initBackground(Context c, AttributeSet attrs) {
		Drawable background = getBackgroundDrawable(c, attrs);
        int searchPlateId = NativeResources.getIdentifier(IDENTIFIER_NAME);
        View searchPlate = findViewById(searchPlateId);
        setBackground(searchPlate, background);
	}
	
	private Drawable getBackgroundDrawable(Context c, AttributeSet attrs) {
		TypedArray a = c.obtainStyledAttributes(attrs,
				R.styleable.AccentSearchView, R.attr.accentSearchViewStyle, 0);
		Drawable result = a.getDrawable(R.styleable.AccentSearchView_android_background);
		a.recycle();
		
		if (result == null)
			return c.getResources().getDrawable(R.drawable.ha__searchview_textfield);
		
		return result;
	}
	
	/** Call the appropriate method to set the background to the View*/
	@SuppressWarnings("deprecation")
	@SuppressLint("NewApi")
	private void setBackground(View view, Drawable drawable) {
        if (Build.VERSION.SDK_INT >= SET_DRAWABLE_MIN_SDK)
        	view.setBackground(drawable);
        else
        	view.setBackgroundDrawable(drawable);
	}

}
