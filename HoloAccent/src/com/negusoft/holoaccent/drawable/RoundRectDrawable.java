package com.negusoft.holoaccent.drawable;

import android.graphics.RectF;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.graphics.drawable.shapes.Shape;
import android.util.DisplayMetrics;
import android.util.TypedValue;

public class RoundRectDrawable extends ShapeDrawable {
	
	private static Shape initShape(DisplayMetrics metrics, float cornerRadiusDp, float borderWidthDp) {
		float cornerRadiusPx = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, cornerRadiusDp, metrics);
		float[] coreners = new float[] { cornerRadiusPx, cornerRadiusPx, cornerRadiusPx, 
				cornerRadiusPx, cornerRadiusPx, cornerRadiusPx, cornerRadiusPx, cornerRadiusPx };
		float borderWidthPx = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, borderWidthDp, metrics);
		RectF inset = new RectF(borderWidthPx, borderWidthPx, borderWidthPx, borderWidthPx);
		return new RoundRectShape(coreners, inset, null);
	}
	
	private static Shape initShape(DisplayMetrics metrics, float cornerRadiusDp) {
		float cornerRadiusPx = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, cornerRadiusDp, metrics);
		float[] coreners = new float[] { cornerRadiusPx, cornerRadiusPx, cornerRadiusPx, 
				cornerRadiusPx, cornerRadiusPx, cornerRadiusPx, cornerRadiusPx, cornerRadiusPx };
		return new RoundRectShape(coreners, null, null);
	}
	
	public RoundRectDrawable(DisplayMetrics metrics, int fillColor, float cornerRadiusDp) {
		super(initShape(metrics, cornerRadiusDp));
		getPaint().setColor(fillColor);
	}
	
	public RoundRectDrawable(DisplayMetrics metrics, int fillColor, float cornerRadiusDp, float borderWidthDp) {
		super(initShape(metrics, cornerRadiusDp, borderWidthDp));
		getPaint().setColor(fillColor);
	}

}
