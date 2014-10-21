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
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.QuickContactBadge;

import com.negusoft.holoaccent.R;

import java.lang.reflect.Field;

/** Extend the QuickContactBadge to set the accent color appropriately. */
public class AccentQuickContactBadge extends QuickContactBadge {

    private static final String OVERLAY_FIELD_NAME = "mOverlay";

    public AccentQuickContactBadge(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    public AccentQuickContactBadge(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public AccentQuickContactBadge(Context context) {
        super(context);
        init(context);
    }

    private void init(Context c) {
        try {
            // Use reflection to programmatically change the overlay drawable
            Field selectionDivider = QuickContactBadge.class.getDeclaredField(OVERLAY_FIELD_NAME);
            selectionDivider.setAccessible(true);
            Drawable drawable = getOverlayDrawable(c);
            selectionDivider.set(this, drawable);
        } catch (NoSuchFieldException e) {
            // ignore
        } catch (IllegalAccessException e) {
            // ignore
        }
    }

    private Drawable getOverlayDrawable(Context c) {
        TypedArray attr = c.obtainStyledAttributes(new int[] { R.attr.accentContactBadgeOverlay });
        Drawable result = attr.getDrawable(0);
        attr.recycle();
        return result;
    }

}
