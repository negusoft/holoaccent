package com.negusoft.holoaccent.example.view;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.Checkable;
import android.widget.TextView;

import com.negusoft.holoaccent.AccentHelper;
import com.negusoft.holoaccent.AccentPalette;
import com.negusoft.holoaccent.AccentResources;
import com.negusoft.holoaccent.activity.AccentActivity;
import com.negusoft.holoaccent.example.R;

public class CheckableTextView extends TextView implements Checkable {
	
	private static final int BACKGROUND_ALPHA = 0x88;
	
	private final AccentPalette mPalette;
	private boolean mChecked = false;

	public CheckableTextView(Context context) {
		super(context);
		int defaultColor = context.getResources().getColor(android.R.color.holo_blue_light);
		TypedArray attrs = context.getTheme().obtainStyledAttributes(R.styleable.HoloAccent);
		int accentColor = attrs.getColor(R.styleable.HoloAccent_accentColor, defaultColor);
		mPalette = initPalette(context);
	}

	public CheckableTextView(Context context, AttributeSet attrSet) {
		super(context, attrSet);
		int defaultColor = context.getResources().getColor(android.R.color.holo_blue_light);
		TypedArray attrs = context.getTheme().obtainStyledAttributes(R.styleable.HoloAccent);
		int accentColor = attrs.getColor(R.styleable.HoloAccent_accentColor, defaultColor);
        mPalette = initPalette(context);
	}

	public CheckableTextView(Context context, AttributeSet attrSet, int defStyle) {
		super(context, attrSet, defStyle);
		int defaultColor = context.getResources().getColor(android.R.color.holo_blue_light);
		TypedArray attrs = context.getTheme().obtainStyledAttributes(R.styleable.HoloAccent);
		int accentColor = attrs.getColor(R.styleable.HoloAccent_accentColor, defaultColor);
        mPalette = initPalette(context);
	}

    private AccentPalette initPalette(Context c) {
        AccentPalette result = AccentHelper.getPalette(c);
        if (result == null) {
            int defaultColor = c.getResources().getColor(android.R.color.holo_blue_light);
            return new AccentPalette(defaultColor);
        }
        return result;
    }

	@Override
	public boolean isChecked() {
		return mChecked;
	}

	@Override
	public void setChecked(boolean checked) {
		mChecked = checked;
		invalidate();
	}

	@Override
	public void toggle() {
		mChecked = !mChecked;
		invalidate();
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		if (mChecked)
			canvas.drawColor(mPalette.getAccentColor(BACKGROUND_ALPHA));
		super.onDraw(canvas);
	}

}
