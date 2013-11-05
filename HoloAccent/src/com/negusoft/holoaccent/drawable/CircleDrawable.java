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
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.DisplayMetrics;
import android.util.TypedValue;

public class CircleDrawable extends Drawable {

	private final CircleConstantState mState;
	private final Paint mFillPaint;
	private final Paint mBorderPaint;
	
	public CircleDrawable(Resources res, float radiusDp, int fillColor, float borderWidthDp, int borderColor) {
		DisplayMetrics metrics = res.getDisplayMetrics();
		mState = new CircleConstantState(metrics, radiusDp, fillColor, borderWidthDp, borderColor);
		mBorderPaint = initBorderPaint(metrics, borderWidthDp, borderColor);
		mFillPaint = initFillPaint(fillColor);
	}
	
	CircleDrawable(DisplayMetrics metrics, float radiusDp, int fillColor, float borderWidthDp, int borderColor) {
		mState = new CircleConstantState(metrics, radiusDp, fillColor, borderWidthDp, borderColor);
		mBorderPaint = initBorderPaint(metrics, borderWidthDp, borderColor);
		mFillPaint = initFillPaint(fillColor);
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
	
	private Paint initFillPaint(int fillColor) {
		if (Color.alpha(fillColor) == 0)
			return null;
		
        Paint result = new Paint();
		result.setColor(fillColor);
		result.setStyle(Paint.Style.FILL);
		result.setAntiAlias(true);
		return result;
	}
	
	@Override
	public void draw(Canvas canvas) {
		Rect r = getBounds();
		float centerX = (r.left + r.right) / 2f;
		float centerY = (r.top + r.bottom) / 2f;
		float radius = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, mState.mRadiusDp, mState.mDisplayMetrics);
		if (mBorderPaint != null) {
			canvas.drawCircle(centerX, centerY, radius, mBorderPaint);
		}
		if (mFillPaint != null) {
			if (mBorderPaint != null)
				radius -= (mBorderPaint.getStrokeWidth() / 2);
			canvas.drawCircle(centerX, centerY, radius, mFillPaint);
		}
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
	
	public static class CircleConstantState extends ConstantState {

		public final DisplayMetrics mDisplayMetrics;
		public final float mRadiusDp;
		public final int mColor;
		public final float mBorderWidthDp;
		public final int mBorderColor;
		
		int changingConfigurationValue;
		
		public CircleConstantState(DisplayMetrics metrics, float radius, int color, float borderWidthDp, int borderColor) {
			mDisplayMetrics = metrics;
			mRadiusDp = radius;
			mColor = color;
			mBorderWidthDp = borderWidthDp;
			mBorderColor = borderColor;
		}

		@Override
		public int getChangingConfigurations() {
			return changingConfigurationValue;
		}

		@Override
		public Drawable newDrawable() {
			return new CircleDrawable(mDisplayMetrics, mRadiusDp, mColor, mBorderWidthDp, mBorderColor);
		}
		
	}

}
