/*******************************************************************************
 * Copyright 2013 NEGU Soft
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package com.negusoft.holoaccent.drawable;

import com.negusoft.holoaccent.R;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.util.DisplayMetrics;
import android.util.TypedValue;

public class ToggleForegroundDrawable extends Drawable {

	private static final float LINE_WIDTH_DP = 2.0f;
	private static final float MARGIN_SIDE_DP = 2.0f;
	private static final float MARGIN_BOTTOM_DP = 10.0f;

    private final Paint mPaint;
    private final ToggleConstantState mState;
	
	public ToggleForegroundDrawable(Context c) {
		Resources res = c.getResources();
		DisplayMetrics displayMetrics = res.getDisplayMetrics();

		TypedArray attrs = c.getTheme().obtainStyledAttributes(R.styleable.HoloAccent);
		int color = attrs.getColor(R.styleable.HoloAccent_accentColor, res.getColor(R.color.ha__accent));
		attrs.recycle();

        mPaint = getPaint(displayMetrics, color);
        float marginSide = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, MARGIN_SIDE_DP, displayMetrics);
        float marginBottom = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, MARGIN_BOTTOM_DP, displayMetrics);
        
        mState = new ToggleConstantState(displayMetrics, color, marginSide, marginBottom);
	}
	
	ToggleForegroundDrawable(DisplayMetrics metrics, int color, float marginSide, float marginBottom) {
		mState = new ToggleConstantState(metrics, color, marginSide, marginBottom);
		mPaint = getPaint(metrics, color);
	}
	
	public ToggleForegroundDrawable(Resources res, int color) {
		DisplayMetrics displayMetrics = res.getDisplayMetrics();
        mPaint = getPaint(displayMetrics, color);
        float marginSide = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, MARGIN_SIDE_DP, displayMetrics);
        float marginBottom = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, MARGIN_BOTTOM_DP, displayMetrics);

        mState = new ToggleConstantState(displayMetrics, color, marginSide, marginBottom);
	}
	
	private Paint getPaint(DisplayMetrics displayMetrics, int color) {
        float lineWidth = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, LINE_WIDTH_DP, displayMetrics);
		Paint result = new Paint();
		result.setColor(color);
		result.setStyle(Paint.Style.STROKE);
		result.setStrokeWidth(lineWidth);
		return result;
	}
	
	/**
	 * It is based on the canvas width and height instead of the bounds 
	 * in order not to consider the margins of the button it is drawn in.
	 */
	@Override
	public void draw(Canvas canvas) {
		float width = canvas.getWidth();
		float margin = (width / 3) + mState.mMarginSide;
		float posY = canvas.getHeight() - mState.mMarginBottom;
		canvas.drawLine(margin, posY, width - margin, posY, mPaint);
	}

	@Override
	public int getOpacity() {
		return PixelFormat.TRANSLUCENT;
	}

	@Override
	public void setAlpha(int alpha) {
		// empty
	}

	@Override
	public void setColorFilter(ColorFilter cf) {
		// empty
	}

	@Override
	public final ConstantState getConstantState() {
		mState.changingConfigurationValue = super.getChangingConfigurations();
		return mState;
	}
	
	public static class ToggleConstantState extends ConstantState {

		public final DisplayMetrics mDisplayMetrics;
		public final int mColor;
		public final float mMarginSide;
		public final float mMarginBottom;
		
		int changingConfigurationValue;
		
		public ToggleConstantState(DisplayMetrics metrics, int color, float marginSide, float marginBottom) {
			mDisplayMetrics = metrics;
			mColor = color;
			mMarginSide = marginSide;
			mMarginBottom = marginBottom;
		}

		@Override
		public int getChangingConfigurations() {
			return changingConfigurationValue;
		}

		@Override
		public Drawable newDrawable() {
			return new ToggleForegroundDrawable(mDisplayMetrics, mColor, mMarginSide, mMarginBottom);
		}
		
	}

}
