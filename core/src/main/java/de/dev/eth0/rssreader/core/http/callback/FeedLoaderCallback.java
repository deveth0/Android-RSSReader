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
//$Id: FeedLoaderCallback.java 80 2015-10-05 11:12:03Z deveth0 $
package de.dev.eth0.rssreader.core.http.callback;

import com.squareup.okhttp.Callback;
import de.dev.eth0.rssreader.core.data.provider.DataProviderHelper;
import de.dev.eth0.rssreader.core.http.precacher.PrecacheHelper;
import de.dev.eth0.rssreader.core.util.NotificationManager;


public interface FeedLoaderCallback extends Callback {

  /**
   * called after onRepsonse and onFailure. Can be used to perform additional actions
   */
  void afterCallback();

  DataProviderHelper getDataProviderHelper();

  NotificationManager getNotificationManger();

  PrecacheHelper getPrecacheHelper();

}
