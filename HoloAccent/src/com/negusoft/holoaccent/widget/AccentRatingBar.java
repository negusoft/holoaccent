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
package com.negusoft.holoaccent.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.widget.RatingBar;

import com.negusoft.holoaccent.AccentHelper;
import com.negusoft.holoaccent.AccentPalette;
import com.negusoft.holoaccent.R;

/** Extends RatingBar to apply the correct accent color. */
public class AccentRatingBar extends RatingBar {

    private static final int GLOW_PRESSED_ALPHA = 0xAA;
    private static final int GLOW_FOCUSED_ALPHA = 0x55;

    private static final float BORDER_RATION = 50f;
    private static final float PRESSED_GLOW_RATION = 5f;
    private static final float FOCUSED_GLOW_RATION = 5f;
    private static final float STAR_INNER_RATION = 6f;
    private static final float STAR_OUTER_RATION = 2.5f;

    private AccentPalette mPalette;

    private Paint mFillPaint;
    private int mBorderColor;
    private int mBorderFilledColor;
    private int mEmptyColor;

    public AccentRatingBar(Context context) {
        super(context);
        init(context, null, R.attr.accentRatingBarStyle);
    }

    public AccentRatingBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, R.attr.accentRatingBarStyle);
    }

    public AccentRatingBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs, defStyle);
    }

    private void init(Context context, AttributeSet attrs, int defStyle) {
        mPalette = AccentHelper.getPalette(context);

        TypedArray a = context.obtainStyledAttributes(attrs,
                R.styleable.AccentRatingBar, defStyle, 0);
        mEmptyColor = a.getColor(R.styleable.AccentRatingBar_android_colorBackground, Color.GRAY);
        mBorderColor = a.getColor(R.styleable.AccentRatingBar_android_colorForeground, Color.BLACK);
        mBorderFilledColor = a.getColor(R.styleable.AccentRatingBar_android_colorForegroundInverse, Color.GRAY);
        a.recycle();

        mFillPaint = initFillPaint();
    }

    private Paint initGlowPaint(float strokeWidth, int alpha) {
        Paint result = new Paint();
        result.setColor(mPalette.getAccentColor());
        result.setStyle(Paint.Style.STROKE);
        result.setStrokeWidth(strokeWidth);
        result.setStrokeJoin(Paint.Join.ROUND);
        result.setColor(mPalette.getAccentColor(alpha));
        result.setAntiAlias(true);
        return result;
    }

    private Paint initFillPaint() {
        Paint result = new Paint();
        result.setStyle(Paint.Style.FILL);
        result.setAntiAlias(false);
        return result;
    }

    private Paint initBorderPaint(float starWidth) {
        Paint result = new Paint();
        result.setStyle(Paint.Style.STROKE);
        result.setStrokeWidth(starWidth / BORDER_RATION);
        result.setStrokeJoin(Paint.Join.ROUND);
        result.setAntiAlias(true);
        return result;
    }

    protected synchronized void onDraw(Canvas canvas) {
        if (mPalette == null) {
            super.onDraw(canvas);
            return;
        }

        float width = getWidth();
        float starWidth = width / getNumStars();
        float centerX = starWidth / 2f;
        float centerY = (getHeight() - getPaddingTop() - getPaddingBottom()) / 2f;
        centerY += getPaddingTop();
        Path starPath = getStarPath(centerX, centerY, starWidth / STAR_INNER_RATION, starWidth / STAR_OUTER_RATION);

        float progress = (float)getProgress() / getMax();
        int nonEmptyStars = Math.round(getNumStars() * progress);

        int[] drawableState = getDrawableState();
        boolean pressed = hasValue(drawableState, android.R.attr.state_pressed);
        boolean focused = hasValue(drawableState, android.R.attr.state_focused);

        drawGlow(canvas, starPath, starWidth, focused, pressed);
        drawBackground(canvas, starPath, starWidth, progress);
        drawBorder(canvas, starPath, starWidth, nonEmptyStars);
    }

    private void drawGlow(Canvas canvas, Path starPath, float starWidth, boolean focused, boolean pressed) {
        if (!focused && !pressed)
            return;

        float strokeWidthRatio = pressed ?  PRESSED_GLOW_RATION : FOCUSED_GLOW_RATION;
        int alpha = pressed ?  GLOW_PRESSED_ALPHA : GLOW_FOCUSED_ALPHA;

        Path path = new Path(starPath);
        Paint glowPaint = initGlowPaint(starWidth / strokeWidthRatio, alpha);

        canvas.save();
        for (int i=0; i<getNumStars(); i++) {
            canvas.drawPath(starPath, glowPaint);
            canvas.translate(starWidth, 0f);
        }
        canvas.restore();
    }

    private void drawBackground(Canvas canvas, Path starPath, float starWidth, float progress) {
        Path path = new Path(starPath);
        mFillPaint.setColor(mEmptyColor);

        canvas.save();
        for (int i=0; i<getNumStars(); i++) {
            canvas.drawPath(starPath, mFillPaint);
            canvas.translate(starWidth, 0f);
        }
        canvas.restore();

        mFillPaint.setColor(mPalette.getAccentColor());
        canvas.save();
        canvas.clipRect(0f, 0f, getWidth() * progress, getHeight());
        for (int i=0; i<getNumStars(); i++) {
            canvas.drawPath(starPath, mFillPaint);
            canvas.translate(starWidth, 0f);
        }
        canvas.restore();
    }

    private void drawBorder(Canvas canvas, Path starPath, float starWidth, int nonEmptyStars) {
        Path path = new Path(starPath);
        Paint borderPaint = initBorderPaint(starWidth);
        borderPaint.setColor(mBorderFilledColor);

        canvas.save();
        for (int i=0; i<nonEmptyStars; i++) {
            canvas.drawPath(starPath, borderPaint);
            canvas.translate(starWidth, 0f);
        }

        borderPaint.setColor(mBorderColor);
        for (int i=nonEmptyStars; i<getNumStars(); i++) {
            canvas.drawPath(starPath, borderPaint);
            canvas.translate(starWidth, 0f);
        }
        canvas.restore();
    }

    private boolean hasValue(int[] allValues, int value) {
        for (int i : allValues) {
            if (i == value)
                return true;
        }
        return false;
    }


    private Path getStarPath(float centerX, float centerY, float innerRadius, float outerRadius) {

        double angle = 2.0*Math.PI/10.0;
        float innerTopX = innerRadius*(float)Math.sin(angle);
        float innerTopY = innerRadius*(float)Math.cos(angle);
        float outerTopX = outerRadius*(float)Math.sin(2f*angle);
        float outerTopY = outerRadius*(float)Math.cos(2f*angle);
        float innerBottomX = innerRadius*(float)Math.sin(3f*angle);
        float innerBottomY = innerRadius*(float)Math.cos(3f*angle);
        float outerBottomX = outerRadius*(float)Math.sin(4f*angle);
        float outerBottomY = outerRadius*(float)Math.cos(4f*angle);

        Path result = new Path();
        result.moveTo(centerX, centerY - outerRadius);

        result.lineTo(centerX + innerTopX, centerY - innerTopY);
        result.lineTo(centerX + outerTopX, centerY - outerTopY);
        result.lineTo(centerX + innerBottomX, centerY - innerBottomY);
        result.lineTo(centerX + outerBottomX, centerY - outerBottomY);

        result.lineTo(centerX, centerY + innerRadius);

        result.lineTo(centerX - outerBottomX, centerY - outerBottomY);
        result.lineTo(centerX - innerBottomX, centerY - innerBottomY);
        result.lineTo(centerX - outerTopX, centerY - outerTopY);
        result.lineTo(centerX - innerTopX, centerY - innerTopY);

        result.close();
        return result;
    }
}
