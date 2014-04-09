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
package com.negusoft.holoaccent.widget;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;
import android.widget.NumberPicker;

import com.negusoft.holoaccent.R;

import java.lang.reflect.Field;

/** Extends NumperPicker to apply the correct accent color to the dividers */
public class AccentNumberPicker extends NumberPicker {

    private static final String DIVIDER_FIELD_NAME = "mSelectionDivider";

    public AccentNumberPicker(Context context) {
        super(context);
        init(context);
    }

    public AccentNumberPicker(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public AccentNumberPicker(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    private void init(Context c) {
        try {
            // Use reflection to programmatically change the divider color
            Field selectionDivider = NumberPicker.class.getDeclaredField(DIVIDER_FIELD_NAME);
            selectionDivider.setAccessible(true);
            int color = c.getResources().getColor(R.color.ha__picker_divider);
            selectionDivider.set(this, new ColorDrawable(color));
        } catch (NoSuchFieldException e) {
            // ignore
        } catch (IllegalAccessException e) {
            // ignore
        }
    }
}
