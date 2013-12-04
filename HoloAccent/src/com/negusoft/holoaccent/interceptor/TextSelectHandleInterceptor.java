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
import com.negusoft.holoaccent.drawable.TextSelectHandleDrawable;
import com.negusoft.holoaccent.drawable.TextSelectHandleDrawable.HandleType;

public class TextSelectHandleInterceptor implements AccentResources.Interceptor {
	
	@Override
	public Drawable getDrawable(Resources res, AccentPalette palette, int resId) {
		if (resId == R.drawable.ha__text_select_handle_left_accent)
			return new TextSelectHandleDrawable(res.getDisplayMetrics(), palette, HandleType.LEFT);
		if (resId == R.drawable.ha__text_select_handle_right_accent)
			return new TextSelectHandleDrawable(res.getDisplayMetrics(), palette, HandleType.RIGHT);
		if (resId == R.drawable.ha__text_select_handle_middle_accent)
			return new TextSelectHandleDrawable(res.getDisplayMetrics(), palette, HandleType.MIDDLE);
		return null;
	}

}
