/* ===================================================
 * Copyright 2015 Alex Muthmann
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * ========================================================== */
package de.dev.eth0.rssreader.app.ui.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.text.TextUtils;
import de.dev.eth0.rssreader.R;
import de.dev.eth0.rssreader.app.RssReaderApplication;
import de.dev.eth0.rssreader.app.http.HttpLoaderImpl;
import de.dev.eth0.rssreader.core.Constants;
import timber.log.Timber;

/**
 * Fragment for settings
 */
public class SettingsFragment extends PreferenceFragment implements SharedPreferences.OnSharedPreferenceChangeListener {

  @Override
  public void onCreate(Bundle pSavedInstanceState) {
    super.onCreate(pSavedInstanceState);
    addPreferencesFromResource(R.xml.settings);
  }

  @Override
  public void onResume() {
    super.onResume();
    getPreferenceManager().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);

  }

  @Override
  public void onPause() {
    getPreferenceManager().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
    super.onPause();
  }

  @Override
  public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
    Timber.d("Preference changed %s", key);
    if (TextUtils.equals(key, Constants.KEY_PREF_UPDATE_FREQUENCY)) {
      RssReaderApplication app = (RssReaderApplication)getActivity().getApplication();
      app.setUpdateAlarm();
    }
    else if (TextUtils.equals(key, Constants.KEY_PREF_CACHE_SIZE)) {
      HttpLoaderImpl.updateCacheSize(getActivity());
    }

  }

}
