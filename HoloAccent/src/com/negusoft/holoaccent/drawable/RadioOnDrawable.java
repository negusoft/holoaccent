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
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.RadialGradient;
import android.graphics.Rect;
import android.graphics.Shader.TileMode;
import android.graphics.drawable.Drawable;
import android.util.DisplayMetrics;
import android.util.TypedValue;

import com.negusoft.holoaccent.AccentPalette;

public class RadioOnDrawable extends Drawable {

	private static final float CENTER_RADIUS_DP = 4.75f;
	private static final float GLOW_RADIUS_DP = 8.5f;
	private static final float CENTER_GLOW_WIDTH_DP = 1.0f;
	private static final float RING_BORDER_WIDTH_DP = 0.8f;
	
	private final RadioOnConstantState mState;
	private final Paint mCenterPaint;
	private final Paint mRingPaint;
	
	public RadioOnDrawable(DisplayMetrics metrics, AccentPalette palette) {
		mState = new RadioOnConstantState(metrics, palette);
		mCenterPaint = getCenterPaint(palette);
		mRingPaint = getRingPaint(metrics, palette);
	}
	
	private Paint getCenterGlowPaint(AccentPalette palette, float centerY, float radius) {
		LinearGradient gradient = new LinearGradient(0f, centerY - radius, 0f, centerY, 0x88ffffff, palette.getDarkAccentColor(), TileMode.CLAMP);
		Paint result = new Paint();
		result.setShader(gradient);
		result.setStyle(Paint.Style.STROKE);
		float width = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, CENTER_GLOW_WIDTH_DP, mState.mDisplayMetrics);
		result.setStrokeWidth(width);
		result.setAntiAlias(true);
		result.setDither(true);
		return result;
	}
	
	private Paint getGlowPaint(AccentPalette palette, float centerX, float centerY, float radius) {
		RadialGradient gradient = new RadialGradient(centerX, centerY, radius, palette.accentColor, palette.getAccentColor(0), TileMode.CLAMP);
		Paint result = new Paint();
		result.setShader(gradient);
		result.setStyle(Paint.Style.FILL);
		result.setAntiAlias(true);
		result.setDither(true);
		return result;
	}
	
	private Paint getCenterPaint(AccentPalette palette) {
		Paint result = new Paint();
		result.setColor(palette.getDarkAccentColor());
		result.setStyle(Paint.Style.FILL);
		result.setAntiAlias(true);
		return result;
	}
	
	private Paint getRingPaint(DisplayMetrics metrics, AccentPalette palette) {
		float width = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, RING_BORDER_WIDTH_DP, mState.mDisplayMetrics);
		Paint result = new Paint();
		result.setColor(0x883d3d3d);
		result.setStyle(Paint.Style.STROKE);
		result.setStrokeWidth(width);
		result.setAntiAlias(true);
		return result;
	}
	
	@Override
	public void draw(Canvas canvas) {
		Rect r = getBounds();
		float centerX = r.exactCenterX();
		float centerY = r.exactCenterY();
		
		//background glow
		float glowRadius = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, GLOW_RADIUS_DP, mState.mDisplayMetrics);
		Paint glowPaint = getGlowPaint(mState.mPalette, centerX, centerY, glowRadius);
		canvas.drawCircle(centerX, centerY, glowRadius, glowPaint);
		
		//center backgound
		float centerRadius = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, CENTER_RADIUS_DP, mState.mDisplayMetrics);
		canvas.drawCircle(centerX, centerY, centerRadius, mCenterPaint);
		
		//center shine
		Paint centerPaint = getCenterGlowPaint(mState.mPalette, centerY, centerRadius);
		float shineRadius = centerRadius - (centerPaint.getStrokeWidth() / 2f);
		canvas.drawCircle(centerX, centerY, shineRadius, centerPaint);
		
		//black ring
		float ringRadius = centerRadius + (mRingPaint.getStrokeWidth() / 2f);
		canvas.drawCircle(centerX, centerY, ringRadius, mRingPaint);
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
	
	public static class RadioOnConstantState extends ConstantState {

		public final DisplayMetrics mDisplayMetrics;
		public final AccentPalette mPalette;
		
		int changingConfigurationValue;
		
		public RadioOnConstantState(DisplayMetrics metrics, AccentPalette palette) {
			mDisplayMetrics = metrics;
			mPalette = palette;
		}

		@Override
		public int getChangingConfigurations() {
			return changingConfigurationValue;
		}

		@Override
		public Drawable newDrawable() {
			return new RadioOnDrawable(mDisplayMetrics, mPalette);
		}
		
	}

}
