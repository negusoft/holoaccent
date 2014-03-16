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
import android.graphics.drawable.Drawable;

import com.negusoft.holoaccent.AccentPalette;
import com.negusoft.holoaccent.AccentResources;
import com.negusoft.holoaccent.R;
import com.negusoft.holoaccent.drawable.ActionBarBackgroundDrawable;

/** Drawables for the bottom and stacked action bars. Only used in ColoredActionBar themes. */
public class ActionBarBackgroundInterceptor implements AccentResources.Interceptor {

    private static final float LINE_WIDTH_DP_BOTTOM = 1.5f;
    private static final float LINE_WIDTH_DP_STACKED = 1f;

    private final Context mContext;

    public ActionBarBackgroundInterceptor(Context c) {
        mContext = c;
    }

	@Override
	public Drawable getDrawable(Resources res, AccentPalette palette, int resId) {
        if (resId == R.drawable.ha__ab_background_reference)
            return new ActionBarBackgroundDrawable(mContext, palette, R.attr.coloredActionBarBackgroundStyle, false);
        if (resId == R.drawable.ha__ab_background_bottom_reference)
            return new ActionBarBackgroundDrawable(mContext, palette, R.attr.coloredActionBarSplitBackgroundStyle, true);
        if (resId == R.drawable.ha__ab_background_stacked_reference)
            return new ActionBarBackgroundDrawable(mContext, palette, R.attr.coloredActionBarStackedBackgroundStyle, false);
		return null;
	}

}
