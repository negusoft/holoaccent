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

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.DisplayMetrics;
import android.util.TypedValue;

import com.negusoft.holoaccent.AccentPalette;
import com.negusoft.holoaccent.R;

public class ActionBarBackgroundDrawable extends Drawable {

    private static final int DEFAULT_OPACITY = 255;

    private final Paint mFillPaint;
    private final ActionBarBackgroundConstantState mState;
    private final Paint mPaint;

    public ActionBarBackgroundDrawable(Context c, AccentPalette palette, int styleAttribute, boolean overline) {
        TypedArray attrs = c.obtainStyledAttributes(null, R.styleable.ColoredActionBarStacked, styleAttribute, 0);
        int lineColor = attrs.getColor(R.styleable.ColoredActionBarStacked_accentLineColor, palette.getDarkAccentColor());
        float lineWidthDp = attrs.getDimension(R.styleable.ColoredActionBarStacked_accentLineWidth, 0f);
        int lineOpacity = attrs.getInteger(R.styleable.ColoredActionBarStacked_accentLineOpacity, DEFAULT_OPACITY);
        int backgroundColor = attrs.getColor(R.styleable.ColoredActionBarStacked_accentBackgroundColor, palette.getDarkAccentColor());
        int backgroundOpacity = attrs.getInteger(R.styleable.ColoredActionBarStacked_accentBackgroundOpacity, DEFAULT_OPACITY);

        lineColor = Color.argb(lineOpacity, Color.red(lineColor), Color.green(lineColor), Color.blue(lineColor));
        backgroundColor = Color.argb(backgroundOpacity, Color.red(backgroundColor), Color.green(backgroundColor), Color.blue(backgroundColor));

        attrs.recycle();

        DisplayMetrics metrics = c.getResources().getDisplayMetrics();
        mState = new ActionBarBackgroundConstantState(metrics, backgroundColor, lineColor, lineWidthDp, overline);
        mPaint = initLinePaint(metrics, lineColor, lineWidthDp);
        mFillPaint = initFillPaint(backgroundColor);
    }

    public ActionBarBackgroundDrawable(Resources resources, int backgroundColor, int lineColor, float lineWidthDp, boolean overline) {
        DisplayMetrics metrics = resources.getDisplayMetrics();
        mState = new ActionBarBackgroundConstantState(metrics, backgroundColor, lineColor, lineWidthDp, overline);
        mPaint = initLinePaint(metrics, lineColor, lineWidthDp);
        mFillPaint = initFillPaint(backgroundColor);
    }

    private ActionBarBackgroundDrawable(DisplayMetrics metrics, int backgroundColor, int lineColor, float lineWidthDp, boolean overline) {
        mState = new ActionBarBackgroundConstantState(metrics, backgroundColor, lineColor, lineWidthDp, overline);
        mPaint = initLinePaint(metrics, lineColor, lineWidthDp);
        mFillPaint = initFillPaint(backgroundColor);
    }

    private Paint initFillPaint(int color) {
        Paint paint = new Paint();
        paint.setColor(color);
        paint.setStyle(Paint.Style.FILL);
        return paint;
    }

    private Paint initLinePaint(DisplayMetrics displayMetrics, int color, float lineWidthDp) {
        float lineWidth = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, lineWidthDp, displayMetrics);
        Paint result = new Paint();
        result.setColor(color);
        result.setStyle(Paint.Style.STROKE);
        result.setStrokeWidth(lineWidth);
        return result;
    }

    @Override
    public void draw(Canvas canvas) {
        Rect r = new Rect(getBounds());
        if (mState.mLineWidth > 0f) {
            float margin = mPaint.getStrokeWidth() / 2f;
            float posY = mState.mOverline ? r.top + margin : r.bottom - margin;
            canvas.drawLine(r.left, posY, r.right, posY, mPaint);

            if (mState.mOverline)
                r.top += mState.mLineWidth;
            else
                r.bottom -= mState.mLineWidth;
        }
        if (Color.alpha(mState.mBackgroundColor) > 0)
            canvas.drawRect(r, mFillPaint);
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
    public ConstantState getConstantState() {
        mState.changingConfigurationValue = super.getChangingConfigurations();
        return mState;
    }

    public static class ActionBarBackgroundConstantState extends ConstantState {

        public final DisplayMetrics mDisplayMetrics;
        public final int mBackgroundColor;
        public final int mLineColor;
        public final float mLineWidth;
        public final boolean mOverline;

        int changingConfigurationValue;

        public ActionBarBackgroundConstantState(DisplayMetrics metrics, int backgroundColor, int lineColor, float lineWidth, boolean overline) {
            mDisplayMetrics = metrics;
            mBackgroundColor = backgroundColor;
            mLineColor = lineColor;
            mLineWidth = lineWidth;
            mOverline = overline;
        }

        @Override
        public int getChangingConfigurations() {
            return changingConfigurationValue;
        }

        @Override
        public Drawable newDrawable() {
            return new ActionBarBackgroundDrawable(mDisplayMetrics, mBackgroundColor, mLineColor, mLineWidth, mOverline);
        }

    }
}
