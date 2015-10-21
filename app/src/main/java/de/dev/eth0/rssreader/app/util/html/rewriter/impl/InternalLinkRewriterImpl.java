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
package de.dev.eth0.rssreader.app.util.html.rewriter.impl;

import android.content.Context;
import android.text.style.ClickableSpan;
import android.view.View;
import de.dev.eth0.rssreader.BuildConfig;
import de.dev.eth0.rssreader.app.data.provider.DataProviderHelperImpl;
import de.dev.eth0.rssreader.app.util.ActivityStarterHelper;
import de.dev.eth0.rssreader.app.util.html.rewriter.AbstractRewriter;
import de.dev.eth0.rssreader.core.data.model.FeedEntryMetadata;
import de.dev.eth0.rssreader.core.data.provider.DataProviderHelper;
import timber.log.Timber;

public class InternalLinkRewriterImpl extends AbstractRewriter {

  private final Context context;

  public InternalLinkRewriterImpl(Context context) {
    this.context = context;
  }

  @Override
  public boolean accepts(String url) {
    return url.startsWith(BuildConfig.PAGE_URL);
  }


  @Override
  public Object rewrite(final String url) {
    Timber.d("Rewriting url %s to internal link", url);
    DataProviderHelper dph = new DataProviderHelperImpl(context);
    final FeedEntryMetadata metadata = dph.getFeedEntryMetadataForUrl(url);
    if (metadata != null) {
      Timber.d("Found internal matching, rewriting link");
      return new ClickableSpan() {
        @Override
        public void onClick(View view) {
          ActivityStarterHelper.displayFeedEntry(context, metadata.getId());
        }
      };
    }
    return null;
  }

}
