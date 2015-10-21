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
package de.dev.eth0.rssreader.app.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import de.dev.eth0.rssreader.core.Constants;

/**
 *
 * @author deveth0
 */
public final class PreferenceHelper {

  public static boolean isNotificationsDisabled(Context context) {
    return !getBoolean(context, Constants.KEY_PREF_NOTIFICATION_ENABLED, true);
  }

  public static int getUpdateFrequency(Context context) {
    SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
    return Integer.parseInt(sharedPref.getString(Constants.KEY_PREF_UPDATE_FREQUENCY, String.valueOf(Constants.DEFAULT_UPDATE_FREQUENCY)));
  }

  public static int getCacheSize(Context context) {
    SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
    return Integer.parseInt(sharedPref.getString(Constants.KEY_PREF_CACHE_SIZE, String.valueOf(Constants.DEFAULT_CACHE_SIZE)));
  }

  public static boolean isUpdateWifiOnly(Context context) {
    return getBoolean(context, Constants.KEY_PREF_UPDATE_WIFI_ONLY, false);
  }

  public static boolean isPrecacheImagesEnabled(Context context) {
    return getBoolean(context, Constants.KEY_PREF_CACHE_IMAGES, true);
  }

  public static boolean isPrecachePDFEnabled(Context context) {
    return false;
    //TODO: enable as soon as implemented
    //return getBoolean(context, Constants.KEY_PREF_CACHE_PDF, true);
  }

  private static boolean getBoolean(Context context, String key, boolean defaultValue) {
    SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
    return sharedPref.getBoolean(key, defaultValue);
  }

}
