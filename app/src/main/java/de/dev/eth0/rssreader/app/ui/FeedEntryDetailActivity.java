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

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import butterknife.Bind;
import de.dev.eth0.rssreader.R;
import de.dev.eth0.rssreader.app.data.database.tables.FeedEntryTable;
import de.dev.eth0.rssreader.app.ui.fragments.FeedEntryFragment;
import de.dev.eth0.rssreader.core.data.helper.DataHelper;
import timber.log.Timber;

/**
 *
 * @author deveth0
 */
public class FeedEntryDetailActivity extends AbstractBaseActivity {

  //TODO: add content listener for changes in database
  private long feedId;
  private FragmentStatePagerAdapter fragmentStatePagerAdapter;

  @Bind(R.id.feed_detail_pager)
  protected ViewPager viewPager;

  @Override
  protected void onCreate(Bundle pSavedInstanceState) {
    super.onCreate(pSavedInstanceState);
    // Handles screen rotation
    if (getSupportFragmentManager().findFragmentById(R.id.summary_list) != null) {
      Timber.d("Finishing as there is a summary list available");
      finish();
      return;
    }
    fragmentStatePagerAdapter
            = new FeedEntryFragmentStagePagerAdapter(
                    getSupportFragmentManager(), getDataHelper());
    viewPager.setAdapter(fragmentStatePagerAdapter);
    if (pSavedInstanceState != null && pSavedInstanceState.containsKey(FeedEntryTable.COLUMN_ID)) {
      feedId = pSavedInstanceState.getLong(FeedEntryTable.COLUMN_ID);
    }
    if (getIntent().getExtras() != null && getIntent().getExtras().containsKey(FeedEntryTable.COLUMN_ID)) {
      feedId = getIntent().getExtras().getLong(FeedEntryTable.COLUMN_ID);
    }
    Timber.d("onCreate show %d", feedId);
    viewPager.setCurrentItem(getDataHelper().getDataProviderHelper().getFeedEntryPositionForId(feedId));
  }

  @Override
  protected int getContentView() {
    return R.layout.feed_entry_activity;
  }

  @Override
  protected void onSaveInstanceState(final Bundle outState) {
    super.onSaveInstanceState(outState);
    outState.putLong(FeedEntryTable.COLUMN_ID, feedId);
  }

  @Override
  protected void onRestoreInstanceState(Bundle savedInstanceState) {
    super.onRestoreInstanceState(savedInstanceState);
    if (savedInstanceState != null) {
      feedId = savedInstanceState.getLong(FeedEntryTable.COLUMN_ID);
    }
  }

  private static class FeedEntryFragmentStagePagerAdapter extends FragmentStatePagerAdapter {

    private final DataHelper datahelper;
    private int count = Integer.MIN_VALUE;

    private FeedEntryFragmentStagePagerAdapter(FragmentManager supportFragmentManager, DataHelper dataHelper) {
      super(supportFragmentManager);
      this.datahelper = dataHelper;
    }

    @Override
    public Fragment getItem(int i) {
      Timber.d("getting item with id %d", i);
      Fragment fragment = new FeedEntryFragment();
      Bundle args = new Bundle();
      args.putLong(FeedEntryTable.COLUMN_ID, datahelper.getDataProviderHelper().getFeedEntryIdForPosition(i));
      fragment.setArguments(args);
      return fragment;
    }

    @Override
    public int getCount() {
      if (count == Integer.MIN_VALUE) {
        count = datahelper.getDataProviderHelper().getFeedEntryCount();
      }
      return count;
    }

  }

}
