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
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.RadialGradient;
import android.graphics.Rect;
import android.graphics.Shader.TileMode;
import android.graphics.drawable.Drawable;
import android.util.DisplayMetrics;
import android.util.TypedValue;

public class RadialGradientDrawable extends Drawable {
	
	private final RadialGradientState mState;
	
	public RadialGradientDrawable(DisplayMetrics metrics, int color, float innerRadiusDp, float outerRadiusDp) {
		mState = new RadialGradientState(metrics, color, innerRadiusDp, outerRadiusDp);
	}
	
	@Override
	public void draw(Canvas canvas) {
		Rect r = getBounds();
		float centerX = r.exactCenterX();
		float centerY = r.exactCenterY();
		float border = mState.mOuterRadiusDp - mState.mInnerRadiusDp;
		float radius = mState.mInnerRadiusDp + (border / 2f);
		
		// Translate DP to pixels
		border = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, border, mState.mDisplayMetrics);
		radius = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, radius, mState.mDisplayMetrics);
		
		// Draw
		Paint paint = getPaint(mState.mColor, centerX, centerY, radius, border);
		canvas.drawCircle(centerX, centerY, radius, paint);
	}
	
	private Paint getPaint(int color, float centerX, float centerY, float radius, float borderWidth) {
		int endColor = Color.argb(0, Color.red(color), Color.green(color), Color.blue(color));
		float gradientRadius = radius + (borderWidth / 2f);
		RadialGradient gradient = new RadialGradient(centerX, centerY, gradientRadius, color, endColor, TileMode.CLAMP);
		Paint result = new Paint();
		result.setShader(gradient);
		result.setStyle(Paint.Style.STROKE);
		result.setStrokeWidth(borderWidth);
		result.setAntiAlias(true);
		result.setDither(true);
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
	
	public static class RadialGradientState extends ConstantState {

		public final DisplayMetrics mDisplayMetrics;
		public final int mColor;
		public final float mInnerRadiusDp;
		public final float mOuterRadiusDp;
		
		int changingConfigurationValue;
		
		public RadialGradientState(DisplayMetrics metrics, int color, float innerRadiusDp, float outerRadiusDp) {
			mDisplayMetrics = metrics;
			mColor = color;
			mInnerRadiusDp = innerRadiusDp;
			mOuterRadiusDp = outerRadiusDp;
		}

		@Override
		public int getChangingConfigurations() {
			return changingConfigurationValue;
		}

		@Override
		public Drawable newDrawable() {
			return new RadialGradientDrawable(mDisplayMetrics, mColor, mInnerRadiusDp, mOuterRadiusDp);
		}
		
	}

}
