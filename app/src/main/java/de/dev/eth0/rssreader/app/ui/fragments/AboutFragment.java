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

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceScreen;
import de.dev.eth0.rssreader.R;
import de.dev.eth0.rssreader.app.RssReaderApplication;


/**
 * Fragment for settings
 */
public class AboutFragment extends PreferenceFragment {
  private static final String KEY_ABOUT_VERSION = "about_version";
  private static final String KEY_ABOUT_AUTHOR = "about_author";
  private static final String KEY_ABOUT_AUTHOR_TWITTER = "about_author_twitter";

  @Override
  public void onCreate(Bundle pSavedInstanceState) {
    super.onCreate(pSavedInstanceState);
    addPreferencesFromResource(R.xml.about);
    findPreference(KEY_ABOUT_VERSION).setSummary(((RssReaderApplication)getActivity().getApplication()).applicationVersionName());
  }

  @Override
  public boolean onPreferenceTreeClick(PreferenceScreen pPreferenceScreen, Preference pPreference) {
    String key = pPreference.getKey();
    if (KEY_ABOUT_AUTHOR_TWITTER.equals(key)) {
      startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.about_author_twitter_url))));
      return true;
    }
    else if (KEY_ABOUT_AUTHOR.equals(key)) {
      startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.about_author_url))));
      return true;
    }
    return false;
  }
}
