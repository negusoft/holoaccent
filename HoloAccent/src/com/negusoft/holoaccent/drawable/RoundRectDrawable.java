package com.negusoft.holoaccent.drawable;

import android.graphics.RectF;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.graphics.drawable.shapes.Shape;

public class RoundRectDrawable extends ShapeDrawable {
	
	private static Shape initShape(float cornerRadius, float borderWidth) {
		float[] coreners = new float[] { cornerRadius, cornerRadius, cornerRadius, 
				cornerRadius, cornerRadius, cornerRadius, cornerRadius, cornerRadius };
		RectF inset = new RectF(borderWidth, borderWidth, borderWidth, borderWidth);
		return new RoundRectShape(coreners, inset, null);
	}
	
	private static Shape initShape(float cornerRadius) {
		float[] coreners = new float[] { cornerRadius, cornerRadius, cornerRadius, 
				cornerRadius, cornerRadius, cornerRadius, cornerRadius, cornerRadius };
		return new RoundRectShape(coreners, null, null);
	}
	
	public RoundRectDrawable(int fillColor, float cornerRadius) {
		super(initShape(cornerRadius));
		getPaint().setColor(fillColor);
	}
	
	public RoundRectDrawable(int fillColor, float cornerRadius, float borderWidth) {
		super(initShape(cornerRadius, borderWidth));
		getPaint().setColor(fillColor);
	}
	
	public RoundRectDrawable(int fillColor, float cornerRadius, int padding) {
		this(fillColor, cornerRadius);
		setPadding(padding, padding, padding, padding);
	}

}
