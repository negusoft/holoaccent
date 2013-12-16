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

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.DisplayMetrics;
import android.util.TypedValue;

import com.negusoft.holoaccent.AccentPalette;

/**
 * Simple solid drawable that changes will fill the container based on the current level.
 */
public class ScrubberProgressDrawable extends Drawable {
	
	private static final float LINE_WIDTH_DP = 4f;
	
	private final ScrubberProgressConstantState mState;
	private final Paint mPaint;
	
	public ScrubberProgressDrawable(DisplayMetrics metrics, AccentPalette palette) {
		this(metrics, palette, 255);
	}
	
	public ScrubberProgressDrawable(DisplayMetrics metrics, AccentPalette palette, int alpha) {
		mState = new ScrubberProgressConstantState(metrics, palette, alpha);
		mPaint = initPaint(metrics, palette, alpha);
	}
	
	private Paint initPaint(DisplayMetrics metrics, AccentPalette palette, int alpha) {
		float width = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, LINE_WIDTH_DP, metrics);
		Paint result = new Paint();
		result.setStyle(Paint.Style.STROKE);
		result.setColor(palette.getAccentColor(alpha));
		result.setStrokeWidth(width);
		return result;
	}

	@Override
	public void draw(Canvas canvas) {
		float level = getLevel() / 10000f;
		Rect bounds = getBounds();
		float centerY = bounds.exactCenterY();
		float startX = bounds.left;
		float stopX = startX + (bounds.width() * level);

		canvas.drawLine(bounds.left, centerY, stopX, centerY, mPaint);
	}
	
	@Override
	public int getIntrinsicHeight() {
        int result = (int)mPaint.getStrokeWidth();
        return (result < 1) ? 1 : result;
	}

	@Override
	public int getOpacity() {
		return PixelFormat.TRANSLUCENT;
	}

	@Override
	public void setAlpha(int alpha) {
		float alphaPrecentage = alpha / 255f;
		float resultAlpha = mState.mBaseAlpha * alphaPrecentage;
		int color = mState.mPalette.getAccentColor((int)resultAlpha);
		mPaint.setColor(color);
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
	
	public static class ScrubberProgressConstantState extends ConstantState {

		public final DisplayMetrics mDisplayMetrics;
		public final AccentPalette mPalette;
		public final int mBaseAlpha;
		
		int changingConfigurationValue;
		
		public ScrubberProgressConstantState(DisplayMetrics metrics, AccentPalette palette, int baseAlpha) {
			mDisplayMetrics = metrics;
			mPalette = palette;
			mBaseAlpha = baseAlpha;
		}

		@Override
		public int getChangingConfigurations() {
			return changingConfigurationValue;
		}

		@Override
		public Drawable newDrawable() {
			return new ScrubberProgressDrawable(mDisplayMetrics, mPalette, mBaseAlpha);
		}
		
	}

}
