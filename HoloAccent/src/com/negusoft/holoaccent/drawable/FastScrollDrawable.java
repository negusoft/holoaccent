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
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.util.DisplayMetrics;
import android.util.TypedValue;

import com.negusoft.holoaccent.AccentPalette;

public class FastScrollDrawable extends Drawable {
	
	private static final float WIDTH_DP = 30f;
	private static final float HEIGHT_DP = 54f;
	private static final float FILL_WIDTH_DP = 6f;
	private static final float FILL_HEIGHT_DP = 30f;
	private static final float FILL_RADIUS_DP = 1f;
	private static final float GLOW_WIDTH_DP = 14f;
	private static final float GLOW_HEIGHT_DP = 37f;
	private static final float GLOW_RADIUS_DP = 5f;

	private static final int FILL_ALPHA = 156;
	private static final int GLOW_ALPHA = 63;

	private final DisplayMetrics mMetrics;
	private final AccentPalette mPalette;
	private final boolean mPressed;
	
	private final Paint mFillPaint;
	private final float mFillWidth;
	private final float mFillHeight;
	private final float mFillRadius;

	private final Paint mGlowPaint;
	private final float mGlowWidth;
	private final float mGlowHeight;
	private final float mGlowRadius;
	
	private int mAlpha = 255;

	public FastScrollDrawable(DisplayMetrics metrics, AccentPalette palette, boolean pressed) {
		mMetrics = metrics;
		mPalette = palette;
		mPressed = pressed;
		
		mFillPaint = initFillPaint(palette, pressed);
		mFillWidth = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, FILL_WIDTH_DP, metrics);
		mFillHeight = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, FILL_HEIGHT_DP, metrics);
		mFillRadius = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, FILL_RADIUS_DP, metrics);
		
		mGlowPaint = initGlowPaint(palette, pressed);
		mGlowWidth = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, GLOW_WIDTH_DP, metrics);
		mGlowHeight = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, GLOW_HEIGHT_DP, metrics);
		mGlowRadius = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, GLOW_RADIUS_DP, metrics);
	}
	
	private Paint initFillPaint(AccentPalette palette, boolean pressed) {
		Paint result = new Paint();
		result.setStyle(Paint.Style.FILL);
		int color = getFillColor(palette, pressed, 255);
		result.setColor(color);
		result.setAntiAlias(true);
		return result;
	}
	
	private Paint initGlowPaint(AccentPalette palette, boolean pressed) {
		if (!pressed)
			return null;
		
		Paint result = new Paint();
		result.setStyle(Paint.Style.FILL);
		result.setColor(getGlowColor(palette, pressed, 255));
		result.setAntiAlias(true);
		return result;
	}
	
	private int getFillColor(AccentPalette palette, boolean pressed, int alpha) {
		if (!pressed)
			return palette.getDarkAccentColor(alpha);
		int resultAlpha = FILL_ALPHA * alpha / 255;
		return palette.getAccentColor(resultAlpha);
	}
	
	private int getGlowColor(AccentPalette palette, boolean pressed, int alpha) {
		int resultAlpha = GLOW_ALPHA * alpha / 255;
		return palette.getAccentColor(resultAlpha);
	}
	
	@Override
	public int getIntrinsicWidth() {
		return (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, WIDTH_DP, mMetrics);
	}
	
	@Override
	public int getIntrinsicHeight() {
		return (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, HEIGHT_DP, mMetrics);
	}
	
	@Override
	public void draw(Canvas canvas) {
		float centerX = getBounds().exactCenterX();
		float centerY = getBounds().exactCenterY();
		
		if (mGlowPaint != null) {
			float halfWidth = mGlowWidth / 2f;
			float halfHeight = mGlowHeight / 2f;
			RectF rect = new RectF(centerX - halfWidth, 
					centerY - halfHeight, 
					centerX + halfWidth, 
					centerY + halfHeight);
			mGlowPaint.setColor(getGlowColor(mPalette, mPressed, mAlpha));
			canvas.drawRoundRect(rect, mGlowRadius, mGlowRadius, mGlowPaint);
		}

		float halfWidth = mFillWidth / 2f;
		float halfHeight = mFillHeight / 2f;
		RectF rect = new RectF(centerX - halfWidth, 
				centerY - halfHeight, 
				centerX + halfWidth, 
				centerY + halfHeight);
		mFillPaint.setColor(getFillColor(mPalette, mPressed, mAlpha));
		canvas.drawRoundRect(rect, mFillRadius, mFillRadius, mFillPaint);
	}

	@Override
	public int getOpacity() {
		return PixelFormat.TRANSLUCENT;
	}

	@Override
	public void setAlpha(int alpha) {
		mAlpha = alpha;
		invalidateSelf();
	}

	@Override
	public void setColorFilter(ColorFilter cf) {
		// empty
	}

}
