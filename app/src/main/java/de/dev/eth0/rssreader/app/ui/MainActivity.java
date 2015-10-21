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
package de.dev.eth0.rssreader.app.ui;

import de.dev.eth0.rssreader.R;
import de.dev.eth0.rssreader.app.data.database.tables.FeedEntryTable;
import de.dev.eth0.rssreader.app.util.ActivityStarterHelper;
import timber.log.Timber;

/**
 *
 * @author deveth0
 */
public class MainActivity extends AbstractNavigationDrawerActivity {

  @Override
  protected int getContentView() {
    return R.layout.main_activity;
  }

  @Override
  public int getMenuItemId() {
    return R.id.nav_summaries;
  }

  @Override
  protected void onStart() {
    super.onStart();
    if (getIntent() != null && getIntent().hasExtra(FeedEntryTable.COLUMN_ID)) {
      Timber.d("Starting with entry %d", getIntent().getLongExtra(FeedEntryTable.COLUMN_ID, 0));
      long id = getIntent().getLongExtra(FeedEntryTable.COLUMN_ID, 0);
      getIntent().removeExtra(FeedEntryTable.COLUMN_ID);
      ActivityStarterHelper.displayFeedEntry(this, id);
    }
  }


}
