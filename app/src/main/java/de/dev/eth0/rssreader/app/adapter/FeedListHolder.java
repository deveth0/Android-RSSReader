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
package de.dev.eth0.rssreader.app.adapter;

import android.support.annotation.Nullable;
import android.text.Html;
import android.text.TextUtils;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.Bind;
import de.dev.eth0.rssreader.R;
import de.dev.eth0.rssreader.app.ui.AbstractBaseActivity;
import de.dev.eth0.rssreader.core.data.model.FeedEntryMetadata;

/**
 * @author amuthmann
 */
public class FeedListHolder extends AbstractRecyclerViewHolder<FeedEntryMetadata> {

  @Bind(R.id.feedentry)
  protected LinearLayout feedentry;

  @Bind(R.id.feedentry_title)
  protected TextView feedentryTitle;

  @Bind(R.id.feedentry_description)
  @Nullable
  protected TextView feedentryDescription;

  @Bind(R.id.feedentry_creator)
  protected TextView feedentryCreator;

  @Bind(R.id.feedentry_date)
  protected TextView feedentryDate;

  @Bind(R.id.feedentry_bookmarked)
  protected ImageView feedentryBookmarked;

  /**
   *
   * @param itemView
   * @param activity
   */
  public FeedListHolder(View itemView, AbstractBaseActivity activity) {
    super(itemView, activity);

  }

  @Override
  protected void updateContent() {
    if (getEntry() != null) {
      feedentryTitle.setText(getEntry().getTitle());
      if (feedentryDescription != null && !TextUtils.isEmpty(getEntry().getDescription())) {
        feedentryDescription.setText(Html.fromHtml(getEntry().getDescription()));
      }
      feedentryCreator.setText(getEntry().getCreator());
      feedentryDate.setText(DateUtils.getRelativeTimeSpanString(getEntry().getId()));
      feedentryBookmarked.setVisibility(getEntry().isBookmarked() ? View.VISIBLE : View.GONE);
    }
  }
}
