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
package de.dev.eth0.rssreader.core.http;

import com.squareup.okhttp.Response;
import com.squareup.picasso.Picasso;
import de.dev.eth0.rssreader.core.http.callback.FeedLoaderCallback;
import java.io.IOException;

/**
 *
 */
public interface HttpLoader {

  /**
   * @param callback
   */
  public void loadFeedSummary(FeedLoaderCallback callback);
  /**
   * async call to precache a url
   *
   * @param url
   */
  public void precacheUrl(String url);

  /**
   * sync call to load an url.
   *
   * @param url
   * @return
   * @throws IOException
   */
  public Response loadUrl(String url) throws IOException;

  /**
   * return instance of picasso using the configured cache
   *
   * @return
   */
  public Picasso getPicasso();
}
