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
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.DisplayMetrics;
import android.util.TypedValue;

public class UnderlineDrawable extends Drawable {

	private final UnderlineConstantState mState;
	private final Paint mPaint;
	
	public UnderlineDrawable(Resources res, int color, float lineWidthDp) {
		this(res, color, lineWidthDp, false);
	}
	
	public UnderlineDrawable(Resources res, int color, float lineWidthDp, boolean overline) {
		DisplayMetrics metrics = res.getDisplayMetrics();
		mState = new UnderlineConstantState(metrics, color, lineWidthDp, overline);
		mPaint = initPaint(metrics, color, lineWidthDp);
	}
	
	UnderlineDrawable(DisplayMetrics metrics, int color, float lineWidthDp, boolean overline) {
		mState = new UnderlineConstantState(metrics, color, lineWidthDp, overline);
		mPaint = initPaint(metrics, color, lineWidthDp);
	}
	
	private Paint initPaint(DisplayMetrics displayMetrics, int color, float lineWidthDp) {
        float lineWidth = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, lineWidthDp, displayMetrics);
		Paint result = new Paint();
		result.setColor(color);
		result.setStyle(Paint.Style.STROKE);
		result.setStrokeWidth(lineWidth);
		return result;
	}
	
	@Override
	public void draw(Canvas canvas) {
		Rect r = getBounds();
		float margin = mPaint.getStrokeWidth() / 2f;
		float posY = mState.mOverline ? r.top + margin : r.bottom - margin;
		canvas.drawLine(r.left, posY, r.right, posY, mPaint);
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
	
	public static class UnderlineConstantState extends ConstantState {

		public final DisplayMetrics mDisplayMetrics;
		public final int mColor;
		public final float mLineWidth;
		public final boolean mOverline;
		
		int changingConfigurationValue;
		
		public UnderlineConstantState(DisplayMetrics metrics, int color, float lineWidth, boolean overline) {
			mDisplayMetrics = metrics;
			mColor = color;
			mLineWidth = lineWidth;
			mOverline = overline;
		}

		@Override
		public int getChangingConfigurations() {
			return changingConfigurationValue;
		}

		@Override
		public Drawable newDrawable() {
			return new UnderlineDrawable(mDisplayMetrics, mColor, mLineWidth, mOverline);
		}
		
	}

}
