/*
 * Copyright (C) 2020 Just-CAF
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

package com.android.settings.applications;

import android.content.Context;
import android.content.pm.PackageManager;

import android.util.Log;

import com.android.settings.core.TogglePreferenceController;

public class DisableCalendarPreferenceController extends TogglePreferenceController {

    String TAG = "DisabledAppsPreferenceController";

    private static final String CALENDAR_APP_PACKAGE = "com.android.calendar";

    private OnPreferenceChangedCallback mCallback;

    Boolean DEBUG = true;
    Context mContext;

    public interface OnPreferenceChangedCallback {
        void onPreferenceChanged();
    }

    public DisableCalendarPreferenceController(Context context, String key) {
        super(context, key);

        if (DEBUG) Log.d(TAG, "DisabledAppsPreferenceController(Context context, String key)");
        mContext = context;
    }

    @Override
    public int getAvailabilityStatus() {
        return AVAILABLE;
    }

    @Override
    public boolean isChecked() {
        return mContext.getPackageManager().getApplicationEnabledSetting(CALENDAR_APP_PACKAGE)
                == PackageManager.COMPONENT_ENABLED_STATE_ENABLED;
    }

    @Override
    public boolean setChecked(boolean isChecked) {
        mContext.getPackageManager().setApplicationEnabledSetting(CALENDAR_APP_PACKAGE,
                isChecked ? PackageManager.COMPONENT_ENABLED_STATE_ENABLED
                    : PackageManager.COMPONENT_ENABLED_STATE_DISABLED, 0);

        if (mCallback != null) {
            mCallback.onPreferenceChanged();
        }
        return true;
    }

    public DisableCalendarPreferenceController setCallback(
            OnPreferenceChangedCallback callback) {
        mCallback = callback;
        return this;
    }
}
