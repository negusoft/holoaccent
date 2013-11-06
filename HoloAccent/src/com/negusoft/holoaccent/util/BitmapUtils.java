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
import android.graphics.Color;
import android.graphics.Bitmap.CompressFormat;
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
