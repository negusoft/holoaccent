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
/*
 * Copyright (C) 2012 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.negusoft.holoaccent.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Color;
import android.os.Environment;

public class BitmapUtils {
	
	/**
	 * Creates a copy of the bitmap by replacing the color of every pixel 
	 * by accentColor while keeping the alpha value.
	 * @param bitmap The original bitmap.
	 * @param accentColor The color to apply to every pixel.
	 * @return A copy of the given bitmap with the accent color applied.
	 */
	public static Bitmap applyColor(Bitmap bitmap, int accentColor) {
		int r = Color.red(accentColor);
		int g = Color.green(accentColor);
		int b = Color.blue(accentColor);
		
		int width = bitmap.getWidth();
		int height = bitmap.getHeight();
		int[] pixels = new int[width * height];
		bitmap.getPixels(pixels, 0, width, 0, 0, width, height);
		
		for (int i=0; i<pixels.length; i++) {
			int color = pixels[i];
			int alpha = Color.alpha(color);
			pixels[i] = Color.argb(alpha, r, g, b);
		}
		return Bitmap.createBitmap(pixels, width, height, Bitmap.Config.ARGB_8888);
	}
	
	/**
	 * Creates a copy of the bitmap by calculating the transformation based on the 
	 * original color and applying it to the the destination color.
	 * @param bitmap The original bitmap.
	 * @param originalColor Tint color in the original bitmap.
	 * @param destinationColor Tint color to be applied.
	 * @return A copy of the given bitmap with the tint color changed.
	 */
	public static Bitmap changeTintColor(Bitmap bitmap, int originalColor, int destinationColor) {
		// original tint color
		int[] o = new int[] {
				Color.red(originalColor),
				Color.green(originalColor),
				Color.blue(originalColor) };
		// destination tint color
		int[] d = new int[] {
				Color.red(destinationColor),
				Color.green(destinationColor),
				Color.blue(destinationColor) };
		
		int width = bitmap.getWidth();
		int height = bitmap.getHeight();
		int[] pixels = new int[width * height];
		bitmap.getPixels(pixels, 0, width, 0, 0, width, height);

		int maxIndex = getMaxIndex(o);
		int mintIndex = getMinIndex(o);
		
		for (int i=0; i<pixels.length; i++) {
			int color = pixels[i];
			// pixel color
			int[] p = new int[] {
					Color.red(color),
					Color.green(color),
					Color.blue(color) };
			int alpha = Color.alpha(color);

			float[] transformation = calculateTransformation(o[maxIndex], o[mintIndex], p[maxIndex], p[mintIndex]);
			pixels[i] = applyTransformation(d, alpha, transformation);
		}
		return Bitmap.createBitmap(pixels, width, height, Bitmap.Config.ARGB_8888);
	}
	
	/**
	 * Create a bitmap that contains the transformation to make to create the final 
	 * bitmap with a new tint color. You can then call processTintTransformationMap() 
	 * to get a bitmap with the desired tint.
	 * @param bitmap The original bitmap.
	 * @param tintColor Tint color in the original bitmap.
	 * @return A transformation map to be used with processTintTransformationMap(). The 
	 * transformation values are stored in the red and green values. The alpha value is 
	 * significant and the blue value can be ignored.
	 */
	public static Bitmap createTintTransformationMap(Bitmap bitmap, int tintColor) {
		// tint color
		int[] t = new int[] {
				Color.red(tintColor),
				Color.green(tintColor),
				Color.blue(tintColor) };
		
		int width = bitmap.getWidth();
		int height = bitmap.getHeight();
		int[] pixels = new int[width * height];
		bitmap.getPixels(pixels, 0, width, 0, 0, width, height);

		int maxIndex = getMaxIndex(t);
		int mintIndex = getMinIndex(t);
		
		for (int i=0; i<pixels.length; i++) {
			int color = pixels[i];
			// pixel color
			int[] p = new int[] {
					Color.red(color),
					Color.green(color),
					Color.blue(color) };
			int alpha = Color.alpha(color);

			float[] transformation = calculateTransformation(t[maxIndex], t[mintIndex], p[maxIndex], p[mintIndex]);
			pixels[i] = Color.argb(alpha, (int)(transformation[0]*255), (int)(transformation[1]*255), 0);
		}
		return Bitmap.createBitmap(pixels, width, height, Bitmap.Config.ARGB_8888);
	}
	
	/**
	 * Apply the given tint color to the transformation map.
	 * @param transformationMap A bitmap representing the transformation map.
	 * @param tintColor Tint color to be applied.
	 * @return A bitmap with the with the tint color set.
	 */
	public static Bitmap processTintTransformationMap(Bitmap transformationMap, int tintColor) {
		// tint color
		int[] t = new int[] {
				Color.red(tintColor),
				Color.green(tintColor),
				Color.blue(tintColor) };
		
		int width = transformationMap.getWidth();
		int height = transformationMap.getHeight();
		int[] pixels = new int[width * height];
		transformationMap.getPixels(pixels, 0, width, 0, 0, width, height);
		
		float[] transformation = new float[2];
		
		for (int i=0; i<pixels.length; i++) {
			int color = pixels[i];
			int alpha = Color.alpha(color);
			transformation[0] = Color.red(color) / 255f;
			transformation[1] = Color.green(color) / 255f;
			pixels[i] = applyTransformation(t, alpha, transformation);
		}
		return Bitmap.createBitmap(pixels, width, height, Bitmap.Config.ARGB_8888);
	}
	
	private static int getMaxIndex(int[] values) {
		int result = 0;
		for (int i=1; i<values.length; i++) {
			if (values[result] < values[i])
				result = i;
		}
		return result;
	}
	
	private static int getMinIndex(int[] values) {
		int result = 0;
		for (int i=1; i<values.length; i++) {
			if (values[result] > values[i])
				result = i;
		}
		return result;
	}
	
	private static float[] calculateTransformation(int tintA, int tintB, int colorA, int colorB) {
		float a = tintA / 255f;
		float aP = tintB / 255f;
		float b = colorA / 255f;
		float bP = colorB / 255f;
		
		float[] result = new float[2];
		result[0] = (b-bP) / ((b*aP)+a-(bP*a)-aP);
		result[1] = (bP-(aP*result[0])) / (1f-(aP*result[0]));
		
		return result;
	}
	
	private static int applyTransformation(int[] tintComponents, int alpha, float[] transformation) {
		return Color.argb(
				alpha,
				(int)applyTransformation(tintComponents[0], transformation),
				(int)applyTransformation(tintComponents[1], transformation),
				(int)applyTransformation(tintComponents[2], transformation));
	}
	
	private static float applyTransformation(int colorComponent, float[] transformation) {
		float firstStep = colorComponent*transformation[0];
		return firstStep + ((255-firstStep) * transformation[1]);
	}
	
	/**
	 * Write the given bitmap to a file in the external storage. Requires 
	 * "android.permission.WRITE_EXTERNAL_STORAGE" permission.
	 */
	public static void writeToFile(Bitmap bitmap, String dir, String filename) throws FileNotFoundException, IOException {
		File sdCard = Environment.getExternalStorageDirectory();
		File dirFile = new File (sdCard.getAbsolutePath() + "/" + dir);
		dirFile.mkdirs();
		File f = new File(dirFile, filename);
		FileOutputStream fos = new FileOutputStream(f, false);
		bitmap.compress(CompressFormat.PNG, 100 /*ignored for PNG*/, fos);
		fos.flush();
		fos.close();
	}

}
