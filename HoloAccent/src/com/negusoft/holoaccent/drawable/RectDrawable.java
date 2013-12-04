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
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.util.DisplayMetrics;
import android.util.TypedValue;

public class RectDrawable extends Drawable {

	private final RectConstantState mState;
	private final Paint mFillPaint;
	private final Paint mBorderPaint;
	
	public RectDrawable(Resources res, int fillColor, float borderWidthDp, int borderColor) {
		DisplayMetrics metrics = res.getDisplayMetrics();
		mState = new RectConstantState(metrics, fillColor, borderWidthDp, borderColor);
		mBorderPaint = initBorderPaint(metrics, borderWidthDp, borderColor);
		mFillPaint = initFillPaint(fillColor);
	}
	
	RectDrawable(DisplayMetrics metrics, int fillColor, float borderWidthDp, int borderColor) {
		mState = new RectConstantState(metrics, fillColor, borderWidthDp, borderColor);
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
		if (mFillPaint != null) {
			float fillMargin = 0f;
			if (mBorderPaint != null)
				fillMargin = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, mState.mBorderWidth, mState.mDisplayMetrics);
			float left = r.left + fillMargin;
			float top = r.top + fillMargin;
			float right = r.right - fillMargin;
			float bottom = r.bottom - fillMargin;
			RectF fillRect = new RectF(left, top, right, bottom);
			canvas.drawRect(fillRect, mFillPaint);
		}
		if (mBorderPaint != null) {
			float borderMargin = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, mState.mBorderWidth, mState.mDisplayMetrics);
			borderMargin = borderMargin / 2f;
			float left = r.left + borderMargin;
			float top = r.top + borderMargin;
			float right = r.right - borderMargin;
			float bottom = r.bottom - borderMargin;
			RectF borderRect = new RectF(left, top, right, bottom);
			canvas.drawRect(borderRect, mBorderPaint);
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
	
	public static class RectConstantState extends ConstantState {

		public final DisplayMetrics mDisplayMetrics;
		public final int mColor;
		public final float mBorderWidth;
		public final int mBorderColor;
		
		int changingConfigurationValue;
		
		public RectConstantState(DisplayMetrics metrics, int color, float borderWidth, int borderColor) {
			mDisplayMetrics = metrics;
			mColor = color;
			mBorderWidth = borderWidth;
			mBorderColor = borderColor;
		}

		@Override
		public int getChangingConfigurations() {
			return changingConfigurationValue;
		}

		@Override
		public Drawable newDrawable() {
			return new RectDrawable(mDisplayMetrics, mColor, mBorderWidth, mBorderColor);
		}
		
	}

}
