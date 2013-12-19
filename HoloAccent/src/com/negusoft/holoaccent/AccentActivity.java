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

package com.negusoft.holoaccent;

import android.app.Activity;
import android.content.res.Resources;

public abstract class AccentActivity extends Activity {

    // When no code override of the accent is requested, overrideAccentColor() will return the
    // identity hash of the Integer object on the heap, which is the same one against which we
    // are comparing. Therefore, we will not override the theme. As soon as a user overrides
    // overrideAccentColor(), the identity hash of the returned Integer will be different,
    // and we will override the theme from XML.
    protected final AccentHelper mAccentHelper = new AccentHelper(overrideAccentColor(),
            System.identityHashCode(overrideAccentColor()) != System.identityHashCode
                    (AccentHelper.NO_OVERRIDE));

    @Override
    public Resources getResources() {
        return mAccentHelper.getResources(this, super.getResources());
    }

    /**
     * Programmatically set the accent color.
     *
     * Called in the constructor to determine if the color specified in XML should be overridden.
     * Override in child {@link android.app.Activity Activities}.
     */
    protected Integer overrideAccentColor() {
        // This method MUST return an Integer object rather than the primitive. Returning the
        // primitive will result in really strange behavior.

        // By default do not override
        return AccentHelper.NO_OVERRIDE;
    }

}
