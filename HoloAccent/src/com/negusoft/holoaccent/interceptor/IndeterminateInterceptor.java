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
package com.negusoft.holoaccent.interceptor;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;

import com.negusoft.holoaccent.AccentPalette;
import com.negusoft.holoaccent.AccentResources;
import com.negusoft.holoaccent.R;
import com.negusoft.holoaccent.drawable.IndeterminedProgressDrawable;

public class IndeterminateInterceptor implements AccentResources.Interceptor {

	private final int[] INDETERMINED_DRAWABLE_IDS = new int[] {
		R.drawable.ha__progressbar_indeterminate_1,
		R.drawable.ha__progressbar_indeterminate_2,
		R.drawable.ha__progressbar_indeterminate_3,
		R.drawable.ha__progressbar_indeterminate_4,
		R.drawable.ha__progressbar_indeterminate_5,
		R.drawable.ha__progressbar_indeterminate_6,
		R.drawable.ha__progressbar_indeterminate_7,
		R.drawable.ha__progressbar_indeterminate_8
	};
	
	private final boolean mEnhanced;
	
	public IndeterminateInterceptor(Context c) {
		TypedArray attrs = c.getTheme().obtainStyledAttributes(R.styleable.IndeterminateProgress);
		mEnhanced = attrs.getBoolean(R.styleable.IndeterminateProgress_enhancedIndeterminateProgress, true);
		attrs.recycle();
	}
	
	public IndeterminateInterceptor(boolean enhanced) {
		mEnhanced = enhanced;
	}
	
	@Override
	public Drawable getDrawable(Resources res, AccentPalette palette, int resId) {
		for (int i=0; i< INDETERMINED_DRAWABLE_IDS.length; i++) {
			if (resId == INDETERMINED_DRAWABLE_IDS[i])
				return new IndeterminedProgressDrawable(res, palette.accentColor, i, mEnhanced);
		}
		return null;
	}

}
