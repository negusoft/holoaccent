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
package com.negusoft.holoaccent.dialog;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.Window;
import android.widget.NumberPicker;

import com.negusoft.holoaccent.R;
import com.negusoft.holoaccent.activity.AccentActivity;
import com.negusoft.holoaccent.drawable.IndeterminedProgressDrawable;
import com.negusoft.holoaccent.util.NativeResources;

import java.lang.reflect.Field;

/**
 * Extends the DividerPainter to not only paint the divider in the dialog, but also the dividers
 * in each of the number pickers.
 */
public class DatePickerPainter extends DividerPainter {

    private static final String[] NUMBER_PICKER_ID_NAMES = new String[] {
            "month", "day", "year"
    };

    private static final String NUMBER_PICKER_CLASS_NAME = "android.widget.NumberPicker";
    private static final String DIVIDER_FIELD_NAME = "mSelectionDivider";

    private int mColor;

    public DatePickerPainter(int color) {
        super(color);
    }

	public DatePickerPainter(Context c) {
		super(c);
	}

    @Override
	public void paint(Window window) {
        super.paint(window);

        try {
            for (String idName : NUMBER_PICKER_ID_NAMES) {
                int id = NativeResources.getIdentifier(idName);
                View view = window.findViewById(id);
                if (view == null)
                    continue;
                if (!(view instanceof NumberPicker))
                    continue;

                Class<?> numberPickerClass = Class.forName(NUMBER_PICKER_CLASS_NAME);
                Field selectionDivider = numberPickerClass.getDeclaredField(DIVIDER_FIELD_NAME);
                selectionDivider.setAccessible(true);
                selectionDivider.set(view, new ColorDrawable(getColor()));
            }
        } catch (ClassNotFoundException e) {
            // Ignore
        } catch (NoSuchFieldException e) {
            // Ignore
        } catch (IllegalAccessException e) {
            // Ignore
        }
	}

}
