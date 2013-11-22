package com.negusoft.holoaccent.drawable;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.DisplayMetrics;
import android.util.TypedValue;

public class SpinnerDrawable extends Drawable {

	private static final float LINE_WIDTH_DP = 0.5f;
	private static final float TRIANGLE_SIDE_DP = 12f;
	private static final float VERTICAL_OFFSET_DP = 4f;
	private static final float AB_BOTTOM_MARGIN_DP = 2f;
	
	public enum Type { DEFAULT, DEFAULT_INVERSE, ACTIONBAR, ACTIONBAR_INVERSE };

	private final SpinnerConstantState mState;
	private final Paint mPaint;
	
	public SpinnerDrawable(Resources res, int color, Type type) {
		DisplayMetrics metrics = res.getDisplayMetrics();
		mState = new SpinnerConstantState(metrics, color, type);
		mPaint = initPaint(color);
	}
	
	SpinnerDrawable(DisplayMetrics metrics, int color, Type type) {
		mState = new SpinnerConstantState(metrics, color, type);
		mPaint = initPaint(color);
	}
	
	private Paint initPaint(int fillColor) {
		if (Color.alpha(fillColor) == 0)
			return null;
		
        Paint result = new Paint();
		result.setColor(fillColor);
		result.setStyle(Paint.Style.FILL);
		result.setAntiAlias(true);
		return result;
	}

	@Override
	public void draw(Canvas canvas) {
		Path path = getPath(getBounds(), mState.mType);
		canvas.drawPath(path, mPaint);
	}
	
	private Path getPath(Rect bounds, Type type) {
		switch (type) {
		case DEFAULT:
			return getPathDefault(bounds);
		case DEFAULT_INVERSE:
			return getPathDefaultInverse(bounds);
		case ACTIONBAR:
			return getPathActionbar(bounds);
		case ACTIONBAR_INVERSE:
			return getPathActionbarInverse(bounds);
		default:
			return getPathDefault(bounds);
		}
	}

	private Path getPathDefault(Rect bounds) {
		float lineWidth = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, LINE_WIDTH_DP, mState.mDisplayMetrics);
		float triangleSide = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, TRIANGLE_SIDE_DP, mState.mDisplayMetrics);
		float verticalOffset = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, VERTICAL_OFFSET_DP, mState.mDisplayMetrics);

		float hLeft = bounds.left;
		float hRight = bounds.right + triangleSide;
		float hCenter = bounds.right;
		
		float vBottom = bounds.bottom + verticalOffset;
		float vTop = vBottom - triangleSide;
		float vCenter = vBottom - lineWidth;
		
		Path result = new Path();
		//start from the top corner and go down
		result.moveTo(hRight, vTop);
		result.lineTo(hRight, vBottom);
		result.lineTo(hLeft, vBottom);
		result.lineTo(hLeft, vCenter);
		result.lineTo(hCenter, vCenter);
		result.close();
		
		return result;
	}

	private Path getPathDefaultInverse(Rect bounds) {
		float lineWidth = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, LINE_WIDTH_DP, mState.mDisplayMetrics);
		float triangleSide = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, TRIANGLE_SIDE_DP, mState.mDisplayMetrics);
		float verticalOffset = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, VERTICAL_OFFSET_DP, mState.mDisplayMetrics);

		float hLeft = bounds.left - triangleSide;
		float hRight = bounds.right;
		float hCenter = bounds.left;
		
		float vBottom = bounds.bottom + verticalOffset;
		float vTop = vBottom - triangleSide;
		float vCenter = vBottom - lineWidth;
		
		Path result = new Path();
		//start from the top corner and go down
		result.moveTo(hLeft, vTop);
		result.lineTo(hLeft, vBottom);
		result.lineTo(hRight, vBottom);
		result.lineTo(hRight, vCenter);
		result.lineTo(hCenter, vCenter);
		result.close();
		
		return result;
	}

	private Path getPathActionbar(Rect bounds) {
		float triangleSide = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, TRIANGLE_SIDE_DP, mState.mDisplayMetrics);
		float margin = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, AB_BOTTOM_MARGIN_DP, mState.mDisplayMetrics);

		float left = bounds.right;
		float right = bounds.right + triangleSide;
		float bottom = bounds.bottom - margin;
		float top = bottom - triangleSide;
		
		Path result = new Path();
		//start from the top corner and go down
		result.moveTo(right, top);
		result.lineTo(right, bottom);
		result.lineTo(left, bottom);
		result.close();
		
		return result;
	}

	private Path getPathActionbarInverse(Rect bounds) {
		float triangleSide = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, TRIANGLE_SIDE_DP, mState.mDisplayMetrics);
		float margin = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, AB_BOTTOM_MARGIN_DP, mState.mDisplayMetrics);

		float left = bounds.left - triangleSide;
		float right = bounds.left;
		float bottom = bounds.bottom - margin;
		float top = bottom - triangleSide;
		
		Path result = new Path();
		//start from the top corner and go down
		result.moveTo(left, top);
		result.lineTo(left, bottom);
		result.lineTo(right, bottom);
		result.close();
		
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
	
	public static class SpinnerConstantState extends ConstantState {

		public final DisplayMetrics mDisplayMetrics;
		public final int mColor;
		public final Type mType;
		
		int changingConfigurationValue;
		
		public SpinnerConstantState(DisplayMetrics metrics, int color, Type type) {
			mDisplayMetrics = metrics;
			mColor = color;
			mType = type;
		}

		@Override
		public int getChangingConfigurations() {
			return changingConfigurationValue;
		}

		@Override
		public Drawable newDrawable() {
			return new SpinnerDrawable(mDisplayMetrics, mColor, mType);
		}
		
	}

}
