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
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import de.dev.eth0.rssreader.R;
import de.dev.eth0.rssreader.app.data.database.tables.FeedEntryTable;
import de.dev.eth0.rssreader.app.ui.FeedEntryDetailActivity;
import de.dev.eth0.rssreader.app.ui.fragments.FeedEntryFragment;
import timber.log.Timber;

public class ActivityStarterHelper {

  /**
   * displays the given feed
   *
   * @param id
   * @param context
   */
  public static void displayFeedEntry(Context context, Long id) {
    if (context instanceof FragmentActivity) {
      FragmentActivity activity = (FragmentActivity)context;
      // Depending on the design, we either update the FeedEntryFragment or start a new activity
      FeedEntryFragment feedEntryFragment = (FeedEntryFragment)activity.getSupportFragmentManager().findFragmentById(R.id.feed_detail);
      if (feedEntryFragment != null && feedEntryFragment.isAdded()) {
        Timber.d("Updating feedEntryFragment %d", id);
        feedEntryFragment.displayFeedEntry(id);
      }
      else {
        Timber.d("Starting FeedEntryDetailActivity for Feed %d", id);
        activity.startActivity(new Intent(context, FeedEntryDetailActivity.class).putExtra(FeedEntryTable.COLUMN_ID, id));
      }
    }
    else {
      Timber.e("Could not display feed entry as the context has the wrong class %s", context.getClass());
    }
  }
}
