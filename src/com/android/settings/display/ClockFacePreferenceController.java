/*
 * Copyright (C) 2018 The Android Open Source Project
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

package com.android.settings.display;

import android.content.Context;
import android.provider.Settings;
import android.provider.Settings.Secure;
import android.util.Log;

import androidx.preference.ListPreference;
import androidx.preference.Preference;

import com.android.settings.core.BasePreferenceController;

public class ClockFacePreferenceController extends BasePreferenceController
        implements ListPreference.OnPreferenceChangeListener {

    private static final String KEY_CLOCK_FACES = "lock_screen_custom_clock_face";
    private static final String TAG = "ClockFacePreferenceController";
    static final Boolean DEBUG = true;

    Context mContext;

    public ClockFacePreferenceController(Context context, String key) {
        super(context, key);
        mContext = context;

        if (DEBUG) Log.i(TAG, "ClockFacePreferenceController");
    }

    @Override
    public int getAvailabilityStatus() {
        return AVAILABLE;
    }

    @Override
    public void updateState(Preference preference) {
        final ListPreference listPreference = (ListPreference) preference;
        String face = Settings.Secure.getString(mContext.getContentResolver(), KEY_CLOCK_FACES);

        listPreference.setValue(face);
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        String newFace = (String) newValue;

        Log.i(TAG, "onPreferenceChange");
        Log.i(TAG, newFace);
        Settings.Secure.putString(mContext.getContentResolver(), KEY_CLOCK_FACES, newFace);

        return true;
    }
}
