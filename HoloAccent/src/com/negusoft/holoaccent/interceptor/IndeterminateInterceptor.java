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
			R.drawable.ha__progressbar_indeterminate_legacy_1_reference,
			R.drawable.ha__progressbar_indeterminate_legacy_2_reference,
			R.drawable.ha__progressbar_indeterminate_legacy_3_reference,
			R.drawable.ha__progressbar_indeterminate_legacy_4_reference,
			R.drawable.ha__progressbar_indeterminate_legacy_5_reference,
			R.drawable.ha__progressbar_indeterminate_legacy_6_reference,
			R.drawable.ha__progressbar_indeterminate_legacy_7_reference,
			R.drawable.ha__progressbar_indeterminate_legacy_8_reference
	};

	private final int[] INDETERMINATE_DRAWABLE_IDS = new int[] {
			R.drawable.ha__progressbar_indeterminate_1_reference,
			R.drawable.ha__progressbar_indeterminate_2_reference,
			R.drawable.ha__progressbar_indeterminate_3_reference,
			R.drawable.ha__progressbar_indeterminate_4_reference,
			R.drawable.ha__progressbar_indeterminate_5_reference,
			R.drawable.ha__progressbar_indeterminate_6_reference,
			R.drawable.ha__progressbar_indeterminate_7_reference,
			R.drawable.ha__progressbar_indeterminate_8_reference,
			R.drawable.ha__progressbar_indeterminate_9_reference,
			R.drawable.ha__progressbar_indeterminate_10_reference,
			R.drawable.ha__progressbar_indeterminate_11_reference,
			R.drawable.ha__progressbar_indeterminate_12_reference,
			R.drawable.ha__progressbar_indeterminate_13_reference,
			R.drawable.ha__progressbar_indeterminate_14_reference,
			R.drawable.ha__progressbar_indeterminate_15_reference,
			R.drawable.ha__progressbar_indeterminate_16_reference,
			R.drawable.ha__progressbar_indeterminate_17_reference,
			R.drawable.ha__progressbar_indeterminate_18_reference,
			R.drawable.ha__progressbar_indeterminate_19_reference,
			R.drawable.ha__progressbar_indeterminate_20_reference
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
