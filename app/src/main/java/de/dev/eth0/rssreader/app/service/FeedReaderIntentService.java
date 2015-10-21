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
package de.dev.eth0.rssreader.app.service;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import de.dev.eth0.rssreader.app.http.HttpLoaderImpl;
import de.dev.eth0.rssreader.app.util.PreferenceHelper;
import timber.log.Timber;

/**
 * Service used to read a feed
 *
 * @author amuthmann
 */
public class FeedReaderIntentService extends IntentService {

  public FeedReaderIntentService() {
    super(FeedReaderIntentService.class.getName());
  }

  @Override
  protected void onHandleIntent(Intent intent) {
    Timber.d("onHandleIntent");
    boolean update = true;
    if (PreferenceHelper.isUpdateWifiOnly(this)) {
      Timber.d("Checking if wifi is connected");
      ConnectivityManager connManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
      NetworkInfo networkinfo = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
      update = networkinfo.isConnected();
    }
    if (update) {
      Timber.d("Starting update");
      HttpLoaderImpl.getInstance(this).loadFeedSummary(this);
    }
  }

}
