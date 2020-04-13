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

public class DisableEmailPreferenceController extends TogglePreferenceController {

    String TAG = "DisabledAppsPreferenceController";

    private static final String EMAIL_APP_PACKAGE = "com.android.email";

    private OnPreferenceChangedCallback mCallback;

    Context mContext;

    public interface OnPreferenceChangedCallback {
        void onPreferenceChanged();
    }

    public DisableEmailPreferenceController(Context context, String key) {
        super(context, key);
        mContext = context;
    }

    @Override
    public int getAvailabilityStatus() {
        return AVAILABLE;
    }

    @Override
    public boolean isChecked() {
        return mContext.getPackageManager().getApplicationEnabledSetting(EMAIL_APP_PACKAGE)
                == PackageManager.COMPONENT_ENABLED_STATE_ENABLED;
    }

    @Override
    public boolean setChecked(boolean isChecked) {
        mContext.getPackageManager().setApplicationEnabledSetting(EMAIL_APP_PACKAGE,
                isChecked ? PackageManager.COMPONENT_ENABLED_STATE_ENABLED
                    : PackageManager.COMPONENT_ENABLED_STATE_DISABLED, 0);

        if (mCallback != null) {
            mCallback.onPreferenceChanged();
        }
        return true;
    }

    public DisableEmailPreferenceController setCallback(
            OnPreferenceChangedCallback callback) {
        mCallback = callback;
        return this;
    }
}
