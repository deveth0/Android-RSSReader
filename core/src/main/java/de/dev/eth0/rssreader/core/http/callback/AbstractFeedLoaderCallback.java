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
package de.dev.eth0.rssreader.core.http.callback;

import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import de.dev.eth0.rssreader.core.data.model.FeedEntry;
import de.dev.eth0.rssreader.core.data.parser.RssFeedParser;
import de.dev.eth0.rssreader.core.data.provider.DataProviderHelper;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.List;
import timber.log.Timber;

public abstract class AbstractFeedLoaderCallback implements FeedLoaderCallback {

  @Override
  public void onResponse(Response response) throws IOException {
    Timber.d("onSuccess %d (cache: %s network: %s)", response.code(), response.cacheResponse(), response.networkResponse());
    if (response.isSuccessful() && response.networkResponse() != null && response.networkResponse().code() != HttpURLConnection.HTTP_NOT_MODIFIED) {
      List<FeedEntry> summaries = RssFeedParser.parseFeed(response.body().byteStream());
      DataProviderHelper helper = getDataProviderHelper();
      List<FeedEntry> added = helper.insertFeedEntry(summaries);
      getNotificationManger().sendFeedUpdatedNotification(added);

      getPrecacheHelper().precache(summaries);
    }
    afterCallback();
  }

  @Override
  public void onFailure(Request request, IOException ioe) {
    Timber.w(ioe, "Could not load feed");
    getNotificationManger().sendFeedUpdateFailedNotification(request, ioe);
    afterCallback();
  }

  /**
   * called after onRepsonse and onFailure. Can be used to perform additional actions
   */
  @Override
  public void afterCallback() {

  }

}
