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
 * Seekbar's round drawable. It is drawn in a different way depending 
 * on the SelectorType.
 */
public class ScrubberControlSelectorDrawable extends Drawable {
	
	public enum SelectorType { NORMAL, DISABLED, PRESSED, FOCUSED }

	private static final float DRAWABLE_SIZE_DP = 32f;
	private static final float CENTER_RADIUS_DP = 4.5f;
	private static final float CENTER_RADIUS_SMALL_DP = 2f;
	private static final float OUTER_RADIUS_DP = 14f;
	private static final float OUTER_RADIUS_SMALL_DP = 12f;
	private static final float BORDER_RADIUS_DP = 14f;
	private static final float BORDER_WIDTH_DP = 2f;

	private static final int ALPHA_DEFAULT = 153;
	private static final int ALPHA_PRESSED = 89;
	private static final int ALPHA_FOCUSED = 0x4D;

	private static final int COLOR_DISABLED = 0x4D888888;

	private final CircleConstantState mState;
	private final Paint mCenterPaint;
	private final float mCenterRadius;
	private final Paint mOuterPaint;
	private final float mOuterRadius;
	private final Paint mBorderPaint;
	private final float mBorderRadius;
	
	public ScrubberControlSelectorDrawable(DisplayMetrics metrics, AccentPalette palette, SelectorType type) {
		mState = new CircleConstantState(metrics, palette, type);
		mCenterPaint = initCenterPaint(palette);
		mCenterRadius = initCenterRadius(metrics, type);
		mOuterPaint = initOuterPaint(palette, type);
		mOuterRadius = initOuterRadius(metrics, type);
		mBorderPaint = initBorderPaint(metrics, palette, type);
		mBorderRadius = initBorderRadius(metrics);
	}
	
	private Paint initCenterPaint(AccentPalette palette) {
        Paint result = new Paint();
		result.setColor(palette.accentColor);
		result.setStyle(Paint.Style.FILL);
		result.setAntiAlias(true);
		return result;
	}
	
	private float initCenterRadius(DisplayMetrics metrics, SelectorType type) {
		float dp = type == SelectorType.DISABLED ? CENTER_RADIUS_SMALL_DP : CENTER_RADIUS_DP;
        return (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, metrics);
	}
	
	private Paint initOuterPaint(AccentPalette palette, SelectorType type) {
        Paint result = new Paint();
		result.setColor(getOuterColor(palette, type));
		result.setStyle(Paint.Style.FILL);
		result.setAntiAlias(true);
		return result;
	}
	
	private int getOuterColor(AccentPalette palette, SelectorType type) {
		if (type == SelectorType.DISABLED)
			return COLOR_DISABLED;
		if (type == SelectorType.FOCUSED)
			return palette.getAccentColor(ALPHA_FOCUSED);
		if (type == SelectorType.PRESSED)
			return palette.getAccentColor(ALPHA_PRESSED);
		return palette.getAccentColor(ALPHA_DEFAULT);
	}
	
	private float initOuterRadius(DisplayMetrics metrics, SelectorType type) {
		float dp = type == SelectorType.DISABLED || type == SelectorType.FOCUSED ? OUTER_RADIUS_SMALL_DP : OUTER_RADIUS_DP;
        return (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, metrics);
	}
	
	private Paint initBorderPaint(DisplayMetrics metrics, AccentPalette palette, SelectorType type) {
		if (type != SelectorType.PRESSED)
			return null;
        Paint result = new Paint();
		result.setColor(palette.accentColor);
		result.setStyle(Paint.Style.STROKE);
		float width = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, BORDER_WIDTH_DP, metrics);
		result.setStrokeWidth(width);
		result.setAntiAlias(true);
		return result;
	}
	
	private float initBorderRadius(DisplayMetrics metrics) {
        return (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, BORDER_RADIUS_DP, metrics);
	}

	@Override
	public void draw(Canvas canvas) {
		Rect r = getBounds();
		float centerX = r.exactCenterX();
		float centerY = r.exactCenterY();

		canvas.drawCircle(centerX, centerY, mOuterRadius, mOuterPaint);
		canvas.drawCircle(centerX, centerY, mCenterRadius, mCenterPaint);
		if (mBorderPaint != null)
			canvas.drawCircle(centerX, centerY, mBorderRadius, mBorderPaint);
	}
	
	@Override
	public int getMinimumWidth() {
        return (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, DRAWABLE_SIZE_DP, mState.mDisplayMetrics);
	}
	
	@Override
	public int getMinimumHeight() {
        return (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, DRAWABLE_SIZE_DP, mState.mDisplayMetrics);
	}
	
	@Override
	public int getIntrinsicWidth() {
        return (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, DRAWABLE_SIZE_DP, mState.mDisplayMetrics);
	}
	
	@Override
	public int getIntrinsicHeight() {
        return (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, DRAWABLE_SIZE_DP, mState.mDisplayMetrics);
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
		public final AccentPalette mPalette;
		public final SelectorType mType;
		
		int changingConfigurationValue;
		
		public CircleConstantState(DisplayMetrics metrics, AccentPalette palette, SelectorType type) {
			mDisplayMetrics = metrics;
			mPalette = palette;
			mType = type;
		}

		@Override
		public int getChangingConfigurations() {
			return changingConfigurationValue;
		}

		@Override
		public Drawable newDrawable() {
			return new ScrubberControlSelectorDrawable(mDisplayMetrics, mPalette, mType);
		}
		
	}

}
