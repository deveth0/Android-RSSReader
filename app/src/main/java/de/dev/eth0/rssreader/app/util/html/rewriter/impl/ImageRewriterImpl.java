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
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.style.ClickableSpan;
import android.view.View;
import de.dev.eth0.rssreader.app.data.database.tables.FeedEntryTable;
import de.dev.eth0.rssreader.app.ui.dialog.ImageOverlayDialog;
import de.dev.eth0.rssreader.app.util.html.rewriter.AbstractRewriter;
import de.dev.eth0.rssreader.core.Constants;
import timber.log.Timber;

public class ImageRewriterImpl extends AbstractRewriter {

  private final Context context;

  public ImageRewriterImpl(Context context) {
    this.context = context;
  }

  @Override
  public boolean accepts(String url) {
    return Constants.PATTERN_IMAGES.matcher(url).matches();
  }

  @Override
  public Object rewrite(final String url) {
    Timber.d("Setup Overlay for Image %s", url);
    return new ClickableSpan() {
      @Override
      public void onClick(View view) {
        if (context instanceof FragmentActivity) {
          FragmentActivity activity = (FragmentActivity)context;
          ImageOverlayDialog dialog = new ImageOverlayDialog();
          Bundle bundle = new Bundle();
          bundle.putString(FeedEntryTable.COLUMN_LINK, url);
          dialog.setArguments(bundle);
          dialog.show(activity.getSupportFragmentManager(), dialog.getClass().getSimpleName());
        }
      }
    };
  }

}
