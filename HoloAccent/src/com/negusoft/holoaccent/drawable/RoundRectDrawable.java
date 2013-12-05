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

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.util.DisplayMetrics;
import android.util.TypedValue;

/**
 * Rectangle shape with rounded corners. The size of the corner is the outer 
 * part size, even with a border.
 * <br/><br/>
 * IMPORTANT: If a border is specified, this is drawn over the fill color. So 
 * if the border color is translucent, it will be blended with the background.
 */
public class RoundRectDrawable extends Drawable {

	private final RoundRectConstantState mState;
	private final Paint mFillPaint;
	private final Paint mBorderPaint;

	public RoundRectDrawable(DisplayMetrics metrics, int fillColor, float cornerSizeDp) {
		this(metrics, fillColor, 0f, 0, cornerSizeDp);
	}
	
	public RoundRectDrawable(DisplayMetrics metrics, int fillColor, float borderWidthDp, int borderColor, float cornerSizeDp) {
		mState = new RoundRectConstantState(metrics, fillColor, borderWidthDp, borderColor, cornerSizeDp);
		mBorderPaint = initBorderPaint(metrics, borderWidthDp, borderColor);
		mFillPaint = initFillPaint(metrics, borderWidthDp, fillColor);
	}
	
	private Paint initBorderPaint(DisplayMetrics displayMetrics, float borderWidthDp, int borderColor) {
		if (Color.alpha(borderColor) == 0)
			return null;
		if (borderWidthDp <= 0f)
			return null;
		
        float borderWidth = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, borderWidthDp, displayMetrics);
		Paint result = new Paint();
		result.setColor(borderColor);
		result.setStyle(Paint.Style.STROKE);
		result.setStrokeWidth(borderWidth);
		result.setAntiAlias(true);
		return result;
	}
	
	private Paint initFillPaint(DisplayMetrics displayMetrics, float borderWidthDp, int fillColor) {
		if (Color.alpha(fillColor) == 0)
			return null;

        float borderWidth = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, borderWidthDp, displayMetrics);
        Paint result = new Paint();
		result.setColor(fillColor);
		result.setStyle(Paint.Style.FILL_AND_STROKE);
		result.setStrokeWidth(borderWidth);
		result.setAntiAlias(true);
		return result;
	}
	
	@Override
	public void draw(Canvas canvas) {
		float borderWidth = mBorderPaint != null ? mBorderPaint.getStrokeWidth() : 0f;
		float margin = borderWidth / 2f;
        float cornerSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, mState.mCorenerSize, mState.mDisplayMetrics);
		
        // correct the border so it represents the outer part of the shape (not the center of the border)
        cornerSize -= borderWidth / 2f;
        
        Path path = getPath(getBounds(), margin, cornerSize);
        if (mFillPaint != null)
			canvas.drawPath(path, mFillPaint);
		if (mBorderPaint != null)
			canvas.drawPath(path, mBorderPaint);
	}
	
	private Path getPath(Rect bounds, float margin, float cornerSize) {
		Path result = new Path();
		float top = bounds.top + margin;
		float bottom = bounds.bottom - margin;
		float left = bounds.left + margin;
		float right = bounds.right - margin;
		
		// Top left corner (clockwise)
		RectF topLeftArc = new RectF(
				left,
				top,
				left + cornerSize,
				top + cornerSize
				);
		result.arcTo(topLeftArc, 180, 90);

		// Top right corner (clockwise)
		RectF topRightArc = new RectF(
				right - cornerSize,
				top,
				right,
				top + cornerSize
				);
		result.arcTo(topRightArc, 270, 90);

		// Bottom right corner (clockwise)
		RectF bottomRightArc = new RectF(
				right - cornerSize,
				bottom - cornerSize,
				right,
				bottom
				);
		result.arcTo(bottomRightArc, 0, 90);

		// Bottom left corner (clockwise)
		RectF bottomLeftArc = new RectF(
				left,
				bottom - cornerSize,
				left + cornerSize,
				bottom
				);
		result.arcTo(bottomLeftArc, 90, 90);
		
		result.close();
		return result;
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
	
	public static class RoundRectConstantState extends ConstantState {

		public final DisplayMetrics mDisplayMetrics;
		public final int mColor;
		public final float mBorderWidth;
		public final int mBorderColor;
		public final float mCorenerSize;
		
		int changingConfigurationValue;
		
		public RoundRectConstantState(DisplayMetrics metrics, int color, float borderWidth, int borderColor, float cornerSize) {
			mDisplayMetrics = metrics;
			mColor = color;
			mBorderWidth = borderWidth;
			mBorderColor = borderColor;
			mCorenerSize = cornerSize;
		}

		@Override
		public int getChangingConfigurations() {
			return changingConfigurationValue;
		}

		@Override
		public Drawable newDrawable() {
			return new RoundRectDrawable(mDisplayMetrics, mColor, mBorderWidth, mBorderColor, mCorenerSize);
		}
		
		@Override
		public Drawable newDrawable(Resources res) {
			return new RoundRectDrawable(res.getDisplayMetrics(), mColor, mBorderWidth, mBorderColor, mCorenerSize);
		}
		
	}

}
