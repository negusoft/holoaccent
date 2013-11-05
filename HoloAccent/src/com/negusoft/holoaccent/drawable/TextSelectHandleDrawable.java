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
import android.graphics.Path;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.util.DisplayMetrics;
import android.util.TypedValue;

import com.negusoft.holoaccent.AccentPalette;

public class TextSelectHandleDrawable extends Drawable {
	
	public enum HandleType { LEFT, RIGHT, MIDDLE }

	private static final float MARGIN_INNER_DP = 11.75f;
	private static final float MARGIN_OUTER_DP = 14.25f;
	private static final float MARGIN_SYMMETRIC_DP = 11f;
	private static final float MARGIN_BOTTOM_DP = 18.5f;
	private static final float MARGIN_TOP_INNER_DP = 2.75f;
	private static final float MARGIN_TOP_OUTER_DP = 19.75f;
	private static final float MARGIN_TOP_SYMMETRIC_DP = 15.5f;

	private static final float CORNER_RADIUS_DP = 2f;
	
	private final TextSelectHandleState mState;
	private final Paint mPaint;
	
	public TextSelectHandleDrawable(DisplayMetrics metrics, AccentPalette palette, HandleType type) {
		mState = new TextSelectHandleState(metrics, palette, type);
		mPaint = initPaint(palette);
	}
	
	private Paint initPaint(AccentPalette palette) {
		Paint result = new Paint();
		result.setColor(palette.accentColor);
		result.setStyle(Paint.Style.FILL);
		result.setAntiAlias(true);
		return result;
	}
	
	@Override
	public void draw(Canvas canvas) {
		Rect bounds = getBounds();

		float marginLeft = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, getMarginLeftDp(), mState.mDisplayMetrics);
		float marginRight = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, getMarginRightDp(), mState.mDisplayMetrics);
		float marginTopRight = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, getMarginTopRightDp(), mState.mDisplayMetrics);
		float marginBottom = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, MARGIN_BOTTOM_DP, mState.mDisplayMetrics);
		float cornerRadius = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, CORNER_RADIUS_DP, mState.mDisplayMetrics);
		
		float left = bounds.left + marginLeft;
		float right = bounds.right - marginRight;
		float bottom = bounds.bottom - marginBottom;

		// Prepare the beginning of the path
		Path path = initPath(bounds, left);
		
		// Left arc
		RectF arcLeft = new RectF(
				left,
				bottom - cornerRadius,
				left + cornerRadius,
				bottom
				);
		path.arcTo(arcLeft, 180, -90);
		
		// Right arc
		RectF arcRight = new RectF(
				right - cornerRadius,
				bottom - cornerRadius,
				right,
				bottom
				);
		path.arcTo(arcRight, 90, -90);
		
		// Finalize the path and draw
		path.lineTo(right, bounds.top + marginTopRight);
		path.close();
		canvas.drawPath(path, mPaint);
	}
	
	private Path initPath(Rect bounds, float left) {
		float marginTopLeft = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, getMarginTopLeftDp(), mState.mDisplayMetrics);
		Path result = new Path();
		// IF middle -> set the line from the to middle to the left side
		if (mState.mType == HandleType.MIDDLE) {
			float marginTopCenter = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, MARGIN_TOP_INNER_DP, mState.mDisplayMetrics);
			result.moveTo(bounds.exactCenterX(), bounds.top + marginTopCenter);
			result.lineTo(left, bounds.top + marginTopLeft);
			return result;
		}
		result.moveTo(left, bounds.top + marginTopLeft);
		return result;
	}
	
	private float getMarginLeftDp() {
		if (mState.mType == HandleType.LEFT)
			return MARGIN_OUTER_DP;
		if (mState.mType == HandleType.RIGHT)
			return MARGIN_INNER_DP;
		return MARGIN_SYMMETRIC_DP;
	}
	
	private float getMarginRightDp() {
		if (mState.mType == HandleType.LEFT)
			return MARGIN_INNER_DP;
		if (mState.mType == HandleType.RIGHT)
			return MARGIN_OUTER_DP;
		return MARGIN_SYMMETRIC_DP;
	}
	
	private float getMarginTopLeftDp() {
		if (mState.mType == HandleType.LEFT)
			return MARGIN_TOP_OUTER_DP;
		if (mState.mType == HandleType.RIGHT)
			return MARGIN_TOP_INNER_DP;
		return MARGIN_TOP_SYMMETRIC_DP;
	}
	
	private float getMarginTopRightDp() {
		if (mState.mType == HandleType.LEFT)
			return MARGIN_TOP_INNER_DP;
		if (mState.mType == HandleType.RIGHT)
			return MARGIN_TOP_OUTER_DP;
		return MARGIN_TOP_SYMMETRIC_DP;
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
	
	public static class TextSelectHandleState extends ConstantState {

		public final DisplayMetrics mDisplayMetrics;
		public final AccentPalette mPalette;
		public final HandleType mType;
		
		int changingConfigurationValue;
		
		public TextSelectHandleState(DisplayMetrics metrics, AccentPalette palette, HandleType type) {
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
			return new TextSelectHandleDrawable(mDisplayMetrics, mPalette, mType);
		}
		
	}

}
