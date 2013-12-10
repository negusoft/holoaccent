package com.negusoft.holoaccent.drawable;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.DisplayMetrics;
import android.util.TypedValue;

public class SearchViewDrawable extends Drawable {
	
	private static final float LINED_WIDTH_DP = 1f;
	private static final float HEIGHT_DP = 4f;
	private static final float PADDING_HORIZONTAL_DP = 1.5f;

	private final SearchViewConstantState mState;
	private final Paint mPaint;
	private final float mHeight;
	
	public SearchViewDrawable(DisplayMetrics metrics, int color) {
		mState = new SearchViewConstantState(metrics, color);
		mPaint = initPaint(metrics, color, LINED_WIDTH_DP);
		mHeight = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, HEIGHT_DP, metrics);
	}
	
	private Paint initPaint(DisplayMetrics displayMetrics, int color, float lineWidthDp) {
        float lineWidth = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, lineWidthDp, displayMetrics);
		Paint result = new Paint();
		result.setColor(color);
		result.setStyle(Paint.Style.STROKE);
		result.setStrokeJoin(Paint.Join.MITER);
		result.setStrokeWidth(lineWidth);
		return result;
	}
	
	@Override
	public void draw(Canvas canvas) {
		Path path = getPath(getBounds(), mPaint.getStrokeWidth(), mHeight);
		canvas.drawPath(path, mPaint);
	}
	
	private Path getPath(Rect bounds, float lineWidth, float delimiterHeight) {
		float margin = mPaint.getStrokeWidth() / 2f;
		float top = bounds.bottom - delimiterHeight;
		float bottom = bounds.bottom - margin;
		float left = bounds.left + margin;
		float right = bounds.right - margin;
		
		Path result = new Path();
		result.moveTo(left, top);
		result.lineTo(left, bottom);
		result.lineTo(right, bottom);
		result.lineTo(right, top);
		
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
	
	@Override
	public int getMinimumHeight() {
		return (int)mHeight;
	}
	
	@Override
	public boolean getPadding(Rect padding) {
		float horizontalPadding = TypedValue.applyDimension(
				TypedValue.COMPLEX_UNIT_DIP, PADDING_HORIZONTAL_DP, mState.mDisplayMetrics);
		padding.top = 0;
		padding.bottom = 0;
		padding.left = (int)horizontalPadding;
		padding.right = (int)horizontalPadding;
		return true;
	}
	
	public static class SearchViewConstantState extends ConstantState {

		public final DisplayMetrics mDisplayMetrics;
		public final int mColor;
		
		int changingConfigurationValue;
		
		public SearchViewConstantState(DisplayMetrics metrics, int color) {
			mDisplayMetrics = metrics;
			mColor = color;
		}

		@Override
		public int getChangingConfigurations() {
			return changingConfigurationValue;
		}

		@Override
		public Drawable newDrawable() {
			return new SearchViewDrawable(mDisplayMetrics, mColor);
		}
		
	}

}
