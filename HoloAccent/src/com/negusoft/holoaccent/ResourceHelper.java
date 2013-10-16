/*
 * Copyright (C) 2012 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.negusoft.holoaccent;

import android.content.Context;
import android.content.res.Resources;

/**
 * Helper class to lazily initialize AccentResources from your activities. 
 * Simply add the following code to replace the default android Resources 
 * implementation for AccentResources:
 * <pre><code>
private final ResourceHelper mResourceHelper = new ResourceHelper();
{@literal @}Override
public Resources getResources() {
	return mResourceHelper.getResources(this, super.getResources());
}
 * </code></pre>
 * And don't forget to add the corresponding imports:
 * <pre><code>
import com.negusoft.holoaccent.ResourceHelper;
import android.content.res.Resources;
 * </code></pre>
 */
public class ResourceHelper {
	
	private AccentResources mInstance;
	private boolean initializingFlag = false;
	
	public Resources getResources(Context c, Resources resources) {
		if (mInstance == null) {
			if (initializingFlag)
				return resources;
			
			initializingFlag = true;
			mInstance = new AccentResources(c, resources.getAssets(), 
					resources.getDisplayMetrics(), resources.getConfiguration());
		}
		return mInstance;
	}

}
