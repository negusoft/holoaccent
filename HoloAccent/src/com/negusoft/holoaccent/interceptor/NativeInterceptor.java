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
import com.negusoft.holoaccent.util.NativeResources;

/** Access the android private drawables that we don't have directly access to. */
public class NativeInterceptor implements AccentResources.Interceptor {

	@Override
	public Drawable getDrawable(Resources res, AccentPalette palette, int resId) {
        if (resId == R.drawable.ha__native_ic_cab_done_reference)
            return NativeResources.getDrawable("ic_cab_done_holo_dark");
        if (resId == R.drawable.ha__native_ic_cab_done_light_reference)
            return NativeResources.getDrawable("ic_cab_done_holo_light");
        if (resId == R.drawable.ha__native_ic_menu_copy_reference)
            return NativeResources.getDrawable("ic_menu_copy_holo_dark");
        if (resId == R.drawable.ha__native_ic_menu_copy_light_reference)
            return NativeResources.getDrawable("ic_menu_copy_holo_light");
        if (resId == R.drawable.ha__native_ic_menu_cut_reference)
            return NativeResources.getDrawable("ic_menu_cut_holo_dark");
        if (resId == R.drawable.ha__native_ic_menu_cut_light_reference)
            return NativeResources.getDrawable("ic_menu_cut_holo_light");
        if (resId == R.drawable.ha__native_ic_menu_paste_reference)
            return NativeResources.getDrawable("ic_menu_paste_holo_dark");
        if (resId == R.drawable.ha__native_ic_menu_paste_light_reference)
            return NativeResources.getDrawable("ic_menu_paste_holo_light");
        if (resId == R.drawable.ha__native_ic_menu_selectall_reference)
            return NativeResources.getDrawable("ic_menu_selectall_holo_dark");
        if (resId == R.drawable.ha__native_ic_menu_selectall_light_reference)
            return NativeResources.getDrawable("ic_menu_selectall_holo_light");
        if (resId == R.drawable.ha__native_ab_back_reference)
            return NativeResources.getDrawable("ic_ab_back_holo_dark_am");
        if (resId == R.drawable.ha__native_ab_back_light_reference)
            return NativeResources.getDrawable("ic_ab_back_holo_light_am");
		return null;
	}

}
