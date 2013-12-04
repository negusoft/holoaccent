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
import com.negusoft.holoaccent.drawable.ScrubberControlSelectorDrawable;
import com.negusoft.holoaccent.drawable.ScrubberControlSelectorDrawable.SelectorType;
import com.negusoft.holoaccent.drawable.ScrubberProgressDrawable;

public class ScrubberInterceptor implements AccentResources.Interceptor {
	
	@Override
	public Drawable getDrawable(Resources res, AccentPalette palette, int resId) {
		// control selector
		if (resId == R.drawable.ha__scrubber_control_disabled)
			return new ScrubberControlSelectorDrawable(res.getDisplayMetrics(), palette, SelectorType.DISABLED);
		if (resId == R.drawable.ha__scrubber_control_focused)
			return new ScrubberControlSelectorDrawable(res.getDisplayMetrics(), palette, SelectorType.FOCUSED);
		if (resId == R.drawable.ha__scrubber_control_normal)
			return new ScrubberControlSelectorDrawable(res.getDisplayMetrics(), palette, SelectorType.NORMAL);
		if (resId == R.drawable.ha__scrubber_control_pressed)
			return new ScrubberControlSelectorDrawable(res.getDisplayMetrics(), palette, SelectorType.PRESSED);
		
		// progress indicators
		if (resId == R.drawable.ha__scrubber_comp_primary)
			return new ScrubberProgressDrawable(res.getDisplayMetrics(), palette);
		if (resId == R.drawable.ha__scrubber_comp_secondary)
			return new ScrubberProgressDrawable(res.getDisplayMetrics(), palette, 77);
		return null;
	}

}
