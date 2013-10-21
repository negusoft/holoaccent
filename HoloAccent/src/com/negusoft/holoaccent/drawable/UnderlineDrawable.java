package com.negusoft.holoaccent.drawable;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.util.DisplayMetrics;
import android.util.TypedValue;

public class UnderlineDrawable extends Drawable {

	private final UnderlineConstantState mState;
	private final Paint mPaint;
	
	public UnderlineDrawable(Resources res, int color, float lineWidthDp) {
		DisplayMetrics metrics = res.getDisplayMetrics();
		mState = new UnderlineConstantState(metrics, color, lineWidthDp);
		mPaint = initPaint(metrics, color, lineWidthDp);
	}
	
	UnderlineDrawable(DisplayMetrics metrics, int color, float lineWidthDp) {
		mState = new UnderlineConstantState(metrics, color, lineWidthDp);
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
		float posY = canvas.getHeight() - (mPaint.getStrokeWidth() / 2f);
		canvas.drawLine(0f, posY, canvas.getWidth(), posY, mPaint);
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
		
		int changingConfigurationValue;
		
		public UnderlineConstantState(DisplayMetrics metrics, int color, float lineWidth) {
			mDisplayMetrics = metrics;
			mColor = color;
			mLineWidth = lineWidth;
		}

		@Override
		public int getChangingConfigurations() {
			return changingConfigurationValue;
		}

		@Override
		public Drawable newDrawable() {
			return new UnderlineDrawable(mDisplayMetrics, mColor, mLineWidth);
		}
		
	}

}
