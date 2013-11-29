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
package com.negusoft.holoaccent.preference;

import android.content.Context;
import android.preference.Preference;
import android.util.AttributeSet;
import android.view.View;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityManager;
import android.widget.Checkable;

import com.negusoft.holoaccent.R;
import com.negusoft.holoaccent.widget.AccentSwitch;

/**
 * A {@link Preference} that provides a two-state toggleable option.
 * <p>
 * This preference will store a boolean into the SharedPreferences.
 *
 * @attr ref android.R.styleable#SwitchPreference_summaryOff
 * @attr ref android.R.styleable#SwitchPreference_summaryOn
 * @attr ref android.R.styleable#SwitchPreference_switchTextOff
 * @attr ref android.R.styleable#SwitchPreference_switchTextOn
 * @attr ref android.R.styleable#SwitchPreference_disableDependentsState
 */
public class SwitchPreference extends android.preference.SwitchPreference {

	/** As defined in the SwitchPreference source */
    private class Listener implements android.widget.CompoundButton.OnCheckedChangeListener {
        @Override
        public void onCheckedChanged(android.widget.CompoundButton buttonView, boolean isChecked) {
            if (!callChangeListener(isChecked)) {
                // Listener didn't like it, change it back.
                // CompoundButton will make sure we don't recurse.
                buttonView.setChecked(!isChecked);
                return;
            }

            SwitchPreference.this.setChecked(isChecked);
        }
    }
	
	private boolean mSendClickAccessibilityEvent;
    private final Listener mListener = new Listener();

	public SwitchPreference(Context context) {
		super(context);
		setWidgetLayoutResource(R.layout.ha__switch_preference);
	}

	public SwitchPreference(Context context, AttributeSet attrs) {
		super(context, attrs);
		setWidgetLayoutResource(R.layout.ha__switch_preference);
	}

	public SwitchPreference(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		setWidgetLayoutResource(R.layout.ha__switch_preference);
	}

    @Override
    protected void onBindView(View view) {
        super.onBindView(view);

        View checkableView = view.findViewById(R.id.switchWidget);
        if (checkableView != null && checkableView instanceof Checkable) {
            ((Checkable) checkableView).setChecked(isChecked());

            sendAccessibilityEvent(checkableView);

            if (checkableView instanceof AccentSwitch) {
                final AccentSwitch switchView = (AccentSwitch) checkableView;
                switchView.setTextOn(getSwitchTextOn());
                switchView.setTextOff(getSwitchTextOff());
                switchView.setOnCheckedChangeListener(mListener);
            }
        }
        
        // this function is not accessible but it seems not to be required
//        syncSummaryView(view);
    }

    @Override
    protected void onClick() {
        super.onClick();
        mSendClickAccessibilityEvent = true;
    }
    
    /** As defined in TwoStatePreference source */
    void sendAccessibilityEvent(View view) {
        // Since the view is still not attached we create, populate,
        // and send the event directly since we do not know when it
        // will be attached and posting commands is not as clean.
    	AccessibilityManager accessibilityManager =
    	        (AccessibilityManager)getContext().getSystemService(Context.ACCESSIBILITY_SERVICE);
    	if (accessibilityManager == null)
    		return;
        if (mSendClickAccessibilityEvent && accessibilityManager.isEnabled()) {
            AccessibilityEvent event = AccessibilityEvent.obtain();
            event.setEventType(AccessibilityEvent.TYPE_VIEW_CLICKED);
            view.onInitializeAccessibilityEvent(event);
            view.dispatchPopulateAccessibilityEvent(event);
            accessibilityManager.sendAccessibilityEvent(event);
        }
        mSendClickAccessibilityEvent = false;
    }

}

