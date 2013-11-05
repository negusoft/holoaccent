package com.negusoft.holoaccent.intercepter;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;

import com.negusoft.holoaccent.AccentPalette;
import com.negusoft.holoaccent.AccentResources;
import com.negusoft.holoaccent.R;
import com.negusoft.holoaccent.drawable.IndeterminedProgressDrawable;

public class IndeterminateInterceptor implements AccentResources.Interceptor {

	private final int[] INDETERMINED_DRAWABLE_IDS = new int[] {
		R.drawable.progressbar_indeterminate_1,
		R.drawable.progressbar_indeterminate_2,
		R.drawable.progressbar_indeterminate_3,
		R.drawable.progressbar_indeterminate_4,
		R.drawable.progressbar_indeterminate_5,
		R.drawable.progressbar_indeterminate_6,
		R.drawable.progressbar_indeterminate_7,
		R.drawable.progressbar_indeterminate_8
	};
	
	@Override
	public Drawable getDrawable(Resources res, AccentPalette palette, int resId) {
		for (int i=0; i< INDETERMINED_DRAWABLE_IDS.length; i++) {
			if (resId == INDETERMINED_DRAWABLE_IDS[i])
				return new IndeterminedProgressDrawable(res, palette.accentColor, i);
		}
		return null;
	}

}
