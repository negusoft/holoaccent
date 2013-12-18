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

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.DisplayMetrics;
import android.util.TypedValue;

import com.negusoft.holoaccent.R;

/**
 * Simple drawable implementation to replace the bitmaps that
 * compose the "progress_indeterminate_horizontal_legacy" animation.
 * <br/><br/>
 * There are 8 variants with their own gap positions. The positions 
 * are taken from the original drawables, and represented as a 
 * percentage [0..1] of the whole width of the drawable.
 */
public class IndeterminedProgressLegacyDrawable extends Drawable {

	private static final float MIN_WIDTH_DP = 606.0f;
	private static final float MIN_HEIGHT_DP = 16.0f;
	private static final float LINE_WIDTH_DP = 4.0f;
	private static final float GAP_WIDTH_DP = 4.0f;
	
	// Percentages taken from the original PNG files
	private final float[] GAP_PERCENTAGES_1 = new float[] { 0.07920792f, 0.33333334f, 0.6386139f, 0.8679868f, 0.97359735f };
	private final float[] GAP_PERCENTAGES_2 = new float[] { 0.09405941f, 0.35643566f, 0.660066f, 0.87953794f, 0.9785479f };
	private final float[] GAP_PERCENTAGES_3 = new float[] { 0.0066006603f, 0.15511551f, 0.44884488f, 0.7359736f, 0.9158416f, 0.98349833f };
	private final float[] GAP_PERCENTAGES_4 = new float[] { 0.00990099f, 0.17986798f, 0.4768977f, 0.7590759f, 0.92574257f, 0.9867987f };
	private final float[] GAP_PERCENTAGES_5 = new float[] { 0.02970297f, 0.20957096f, 0.5132013f, 0.7871287f, 0.9389439f, 0.9933993f };
	private final float[] GAP_PERCENTAGES_6 = new float[] { 0.041254126f, 0.24257426f, 0.5478548f, 0.81353134f, 0.9521452f, 0.9966997f };
	private final float[] GAP_PERCENTAGES_7 = new float[] { 0.04950495f, 0.2739274f, 0.5841584f, 0.8349835f, 0.96039605f, 0.99834985f };
	private final float[] GAP_PERCENTAGES_8 = new float[] { 0.06435644f, 0.3069307f, 0.6171617f, 0.8547855f, 0.9686469f };
	
	//Holds all the gap percentages
	private final float[][] GAP_PERCENTAGES_REFERENCE = new float[][] { 
			GAP_PERCENTAGES_1, GAP_PERCENTAGES_2, GAP_PERCENTAGES_3, GAP_PERCENTAGES_4,
			GAP_PERCENTAGES_5, GAP_PERCENTAGES_6, GAP_PERCENTAGES_7, GAP_PERCENTAGES_8 };
	
	private final DisplayMetrics mDisplayMetrics;
    private final Paint mPaint;
    private final float[] mGapPercentages;
	
	public IndeterminedProgressLegacyDrawable(Context c, int index) {
		Resources res = c.getResources();
		mDisplayMetrics = res.getDisplayMetrics();

		TypedArray attrs = c.getTheme().obtainStyledAttributes(R.styleable.HoloAccent);
		int color = attrs.getColor(R.styleable.HoloAccent_accentColor, res.getColor(R.color.ha__accent));
		attrs.recycle();

        mPaint = getPaint(mDisplayMetrics, color);
        
        mGapPercentages = getGapPercentages(index);
	}
	
	public IndeterminedProgressLegacyDrawable(Resources res, int color, int index) {
		mDisplayMetrics = res.getDisplayMetrics();
        mPaint = getPaint(mDisplayMetrics, color);
        mGapPercentages = getGapPercentages(index);
	}
	
	private Paint getPaint(DisplayMetrics displayMetrics, int color) {
        float lineWidth = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, LINE_WIDTH_DP, displayMetrics);
		Paint result = new Paint();
		result.setColor(color);
		result.setStyle(Paint.Style.STROKE);
		result.setStrokeWidth(lineWidth);
		return result;
	}
	
	private float[] getGapPercentages(int index) {
		// Normalize the index just in case
		index = index % GAP_PERCENTAGES_REFERENCE.length;
		return GAP_PERCENTAGES_REFERENCE[index];
	}
	
	@Override
	public int getMinimumWidth() {
		float width = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, MIN_WIDTH_DP, mDisplayMetrics);
		return width < 1.0f ? 1 : (int)width;
	}
	
	@Override
	public int getMinimumHeight() {
		float height = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, MIN_HEIGHT_DP, mDisplayMetrics);
		return height < 1.0f ? 1 : (int)height;
	}

	@Override
	public void draw(Canvas canvas) {
		
		Rect bounds = getBounds();
		
		float totalWidth = bounds.width();
		float minWidth = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, MIN_WIDTH_DP, mDisplayMetrics);
		if (totalWidth < minWidth)
			totalWidth = minWidth;
        
        float centerY = bounds.exactCenterY();
        float startX = bounds.left;
        float stopX;
        float gapWidth = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, GAP_WIDTH_DP, mDisplayMetrics);
        
        // Draw the lines before the gaps
        for (float startPercentage : mGapPercentages) {
        	stopX = totalWidth * startPercentage;
        	canvas.drawLine(startX, centerY, stopX, centerY, mPaint);
        	startX = stopX + gapWidth;
        }
    	canvas.drawLine(startX, centerY, totalWidth, centerY, mPaint);
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

}
