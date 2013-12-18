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

import android.content.res.Resources;
import android.graphics.drawable.Drawable;

import com.negusoft.holoaccent.AccentPalette;
import com.negusoft.holoaccent.AccentResources;
import com.negusoft.holoaccent.R;
import com.negusoft.holoaccent.drawable.IndeterminedProgressDrawable;
import com.negusoft.holoaccent.drawable.IndeterminedProgressLegacyDrawable;

public class IndeterminateInterceptor implements AccentResources.Interceptor {

	private final int[] LEGACY_DRAWABLE_IDS = new int[] {
			R.drawable.ha__progressbar_indeterminate_legacy_1,
			R.drawable.ha__progressbar_indeterminate_legacy_2,
			R.drawable.ha__progressbar_indeterminate_legacy_3,
			R.drawable.ha__progressbar_indeterminate_legacy_4,
			R.drawable.ha__progressbar_indeterminate_legacy_5,
			R.drawable.ha__progressbar_indeterminate_legacy_6,
			R.drawable.ha__progressbar_indeterminate_legacy_7,
			R.drawable.ha__progressbar_indeterminate_legacy_8
	};

	private final int[] INDETERMINATE_DRAWABLE_IDS = new int[] {
			R.drawable.ha__progressbar_indeterminate_1,
			R.drawable.ha__progressbar_indeterminate_2,
			R.drawable.ha__progressbar_indeterminate_3,
			R.drawable.ha__progressbar_indeterminate_4,
			R.drawable.ha__progressbar_indeterminate_5,
			R.drawable.ha__progressbar_indeterminate_6,
			R.drawable.ha__progressbar_indeterminate_7,
			R.drawable.ha__progressbar_indeterminate_8,
			R.drawable.ha__progressbar_indeterminate_9,
			R.drawable.ha__progressbar_indeterminate_10,
			R.drawable.ha__progressbar_indeterminate_11,
			R.drawable.ha__progressbar_indeterminate_12,
			R.drawable.ha__progressbar_indeterminate_13,
			R.drawable.ha__progressbar_indeterminate_14,
			R.drawable.ha__progressbar_indeterminate_15,
			R.drawable.ha__progressbar_indeterminate_16,
			R.drawable.ha__progressbar_indeterminate_17,
			R.drawable.ha__progressbar_indeterminate_18,
			R.drawable.ha__progressbar_indeterminate_19,
			R.drawable.ha__progressbar_indeterminate_20
	};
	
	@Override
	public Drawable getDrawable(Resources res, AccentPalette palette, int resId) {
		for (int i=0; i< INDETERMINATE_DRAWABLE_IDS.length; i++) {
			if (resId == INDETERMINATE_DRAWABLE_IDS[i])
				return new IndeterminedProgressDrawable(res, palette.accentColor, i, INDETERMINATE_DRAWABLE_IDS.length);
		}
		for (int i=0; i< LEGACY_DRAWABLE_IDS.length; i++) {
			if (resId == LEGACY_DRAWABLE_IDS[i])
				return new IndeterminedProgressLegacyDrawable(res, palette.accentColor, i);
		}
		return null;
	}

}
