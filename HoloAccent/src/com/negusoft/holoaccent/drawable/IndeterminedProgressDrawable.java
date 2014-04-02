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
import android.view.animation.Interpolator;

import com.negusoft.holoaccent.R;

/**
 * Drawable implementation to replace the bitmaps that compose 
 * the "progress_indeterminate_horizontal" animation.
 * <br/><br/>
 * The gaps position is calculated when loading
 */
public class IndeterminedProgressDrawable extends Drawable {
	
	private static final int DEFAULT_SECTION_NUMBER = 5;

	private static final float MIN_WIDTH_DP = 256.0f;
	private static final float MIN_HEIGHT_DP = 16.0f;
	private static final float LINE_WIDTH_DP = 4.0f;
	private static final float GAP_WIDTH_DP = 4.0f;
	
	private final DisplayMetrics mDisplayMetrics;
    private final Paint mPaint;
    private final float[] mGapPercentages;

    /** Used to calculate the gap positions in a accelarate shape. */
    private Interpolator mInterpolator = new Interpolator() {
        @Override public float getInterpolation(float value) {
            return value * value;
        }
    };
    /** Used to calculate the gap positions in a accelarate/decelarate shape. */
//    private Interpolator mInterpolator = new Interpolator() {
//        @Override public float getInterpolation(float value) {
//            if (value <= 0.5f)
//                return 2 * value * value;
//            float offset = value - 0.5f;
//            return 0.5f + (2*offset) - (2*offset*offset);
//        }
//    }
	
	public IndeterminedProgressDrawable(Context c, int frameIndex, int frameCount) {
		this(c, frameIndex, frameCount, DEFAULT_SECTION_NUMBER);
	}
	
	public IndeterminedProgressDrawable(Context c, int frameIndex, int frameCount, int sectionCount) {
		Resources res = c.getResources();
		mDisplayMetrics = res.getDisplayMetrics();

		TypedArray attrs = c.getTheme().obtainStyledAttributes(R.styleable.HoloAccent);
		int color = attrs.getColor(R.styleable.HoloAccent_accentColor, res.getColor(R.color.ha__accent_default));
		attrs.recycle();

        mPaint = getPaint(mDisplayMetrics, color);
        
        mGapPercentages = getGapPercentages(frameIndex, frameCount, sectionCount);
	}
	
	public IndeterminedProgressDrawable(Resources res, int color, int frameIndex, int frameCount) {
		this(res, color, frameIndex, frameCount, DEFAULT_SECTION_NUMBER);
	}
	
	public IndeterminedProgressDrawable(Resources res, int color, int frameIndex, int frameCount, int sectionCount) {
		mDisplayMetrics = res.getDisplayMetrics();
        mPaint = getPaint(mDisplayMetrics, color);
        mGapPercentages = getGapPercentages(frameIndex, frameCount, sectionCount);
	}
	
	private Paint getPaint(DisplayMetrics displayMetrics, int color) {
        float lineWidth = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, LINE_WIDTH_DP, displayMetrics);
		Paint result = new Paint();
		result.setColor(color);
		result.setStyle(Paint.Style.STROKE);
		result.setStrokeWidth(lineWidth);
		return result;
	}
	
	private float[] getGapPercentages(int frameIndex, int frameCount, int sectionCount) {
		float sectionWidth = 1f / sectionCount;
		float offset = sectionWidth / frameCount * frameIndex;
		float[] result = new float[sectionCount];
		for (int i=0; i< result.length; i++) {
			float phase = offset + (i * sectionWidth);
			result[i] = mInterpolator.getInterpolation(phase);
		}
		return result;
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

        float gapWidth = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, GAP_WIDTH_DP, mDisplayMetrics);
        float centerY = bounds.exactCenterY();
        float startX = bounds.left;
        float stopX;
        
        totalWidth += gapWidth;
        
		for (float startPercentage : mGapPercentages) {
			stopX = bounds.left + (totalWidth * startPercentage) - gapWidth;
			if (stopX < bounds.left) {
				startX = bounds.left + (totalWidth * startPercentage);
				continue;
			}
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
