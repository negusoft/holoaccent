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
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.DisplayMetrics;
import android.util.TypedValue;

public class ContactBadgeDrawable extends RectDrawable {

    private static final float SIDE_DP = 12f;
    private static final float LINE_WIDTH_DP = 2f;

    private ContactBadgeConstantState mState;
    private final Paint mFillPaint;
    private final Paint mLinePaint;

	public ContactBadgeDrawable(Resources res, int fillColor, float borderWidthDp, int borderColor, int markFillColor, int markLineColor) {
        super(res, fillColor, borderWidthDp, borderColor);
        mState = new ContactBadgeConstantState(res.getDisplayMetrics(), fillColor, borderWidthDp, borderColor, markFillColor, markLineColor);
        mFillPaint = initFillPaint(markFillColor);
        mLinePaint = initLinePaint(res.getDisplayMetrics(), markLineColor);
	}

	ContactBadgeDrawable(DisplayMetrics metrics, int fillColor, float borderWidthDp, int borderColor, int markFillColor, int markLineColor) {
        super(metrics, fillColor, borderWidthDp, borderColor);
        mState = new ContactBadgeConstantState(metrics, fillColor, borderWidthDp, borderColor, markFillColor, markLineColor);
        mFillPaint = initFillPaint(markFillColor);
        mLinePaint = initLinePaint(metrics, markLineColor);
	}

    private Paint initFillPaint(int color) {
        Paint result = new Paint();
        result.setStyle(Paint.Style.FILL);
        result.setColor(color);
        result.setAntiAlias(true);
        return result;
    }

    private Paint initLinePaint(DisplayMetrics metrics, int color) {
        Paint result = new Paint();
        result.setStyle(Paint.Style.FILL);
        result.setColor(color);
        result.setAntiAlias(true);
        return result;
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);

        float sideWidth = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, SIDE_DP, mState.mDisplayMetrics);
        float lineWidth = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, LINE_WIDTH_DP, mState.mDisplayMetrics);

        Path mark = getMarkPath(canvas, sideWidth);
        canvas.drawPath(mark, mFillPaint);

        Path line = getLinePath(canvas, sideWidth, lineWidth);
        canvas.drawPath(line, mLinePaint);
    }

    @Override
    public ConstantState getConstantState() {
        mState.changingConfigurationValue = super.getChangingConfigurations();
        return mState;
    }

    private void drawMark(Canvas canvas) {

    }

    private Path getMarkPath(Canvas canvas, float side) {
        Rect bound = getBounds();
        Path result = new Path();
        result.moveTo(bound.right - side, bound.bottom);
        result.lineTo(bound.right, bound.bottom - side);
        result.lineTo(bound.right, bound.bottom);
        result.close();
        return result;
    }

    private Path getLinePath(Canvas canvas, float side, float width) {
        Rect bound = getBounds();
        Path result = new Path();
        result.moveTo(bound.right - side, bound.bottom);
        result.rLineTo(-width, 0f);
        result.lineTo(bound.right, bound.bottom - side - width);
        result.rLineTo(0f, width);
        result.close();
        return result;
    }
	
	public static class ContactBadgeConstantState extends ConstantState {

		public final DisplayMetrics mDisplayMetrics;
		public final int mColor;
		public final float mBorderWidth;
        public final int mBorderColor;
        public final int mMarkFillColor;
        public final int mMarkLineColor;
		
		int changingConfigurationValue;
		
		public ContactBadgeConstantState(DisplayMetrics metrics, int color, float borderWidth, int borderColor, int markFillColor, int markLineColor) {
			mDisplayMetrics = metrics;
			mColor = color;
			mBorderWidth = borderWidth;
			mBorderColor = borderColor;
            mMarkFillColor = markFillColor;
            mMarkLineColor = markLineColor;
		}

		@Override
		public int getChangingConfigurations() {
			return changingConfigurationValue;
		}

		@Override
		public Drawable newDrawable() {
			return new ContactBadgeDrawable(mDisplayMetrics, mColor, mBorderWidth, mBorderColor, mMarkFillColor, mMarkLineColor);
		}
		
	}

}
