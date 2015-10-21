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
package de.dev.eth0.rssreader.app.http.callback;

import android.content.Context;
import de.dev.eth0.rssreader.app.data.provider.DataProviderHelperImpl;
import de.dev.eth0.rssreader.app.http.precacher.PrecacheHelperImpl;
import de.dev.eth0.rssreader.app.util.NotificationManagerImpl;
import de.dev.eth0.rssreader.core.data.provider.DataProviderHelper;
import de.dev.eth0.rssreader.core.http.callback.AbstractFeedLoaderCallback;
import de.dev.eth0.rssreader.core.http.precacher.PrecacheHelper;
import de.dev.eth0.rssreader.core.util.NotificationManager;

/**
 * Handler used for loading a feed
 *
 * @author amuthmann
 */
public class FeedLoaderCallbackImpl extends AbstractFeedLoaderCallback {

  private final Context context;
  private NotificationManager notifcationManager;
  private DataProviderHelper dataProviderHelper;

  public FeedLoaderCallbackImpl(Context context) {
    this.context = context;
  }

  @Override
  public DataProviderHelper getDataProviderHelper() {
    if (dataProviderHelper == null) {
      dataProviderHelper = new DataProviderHelperImpl(context);
    }
    return dataProviderHelper;
  }

  @Override
  public NotificationManager getNotificationManger() {
    if (notifcationManager == null) {
      notifcationManager = new NotificationManagerImpl(context);
    }
    return notifcationManager;
  }

  @Override
  public PrecacheHelper getPrecacheHelper() {
    return new PrecacheHelperImpl(context);
  }

}
