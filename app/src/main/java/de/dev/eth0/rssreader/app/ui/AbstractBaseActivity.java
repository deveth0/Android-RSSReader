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

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.NavUtils;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import butterknife.Bind;
import butterknife.ButterKnife;
import de.dev.eth0.rssreader.R;
import de.dev.eth0.rssreader.app.RssReaderApplication;
import de.dev.eth0.rssreader.app.data.helper.DataHelperImpl;
import de.dev.eth0.rssreader.core.data.helper.DataHelper;

/**
 * Base-class for all activities
 */
public abstract class AbstractBaseActivity extends AppCompatActivity {

  private RssReaderApplication application;

  private DataHelper dataHelper;

  @Bind(R.id.toolbar)
  @Nullable
  protected Toolbar toolbar;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    application = (RssReaderApplication)getApplication();

    setContentView(getContentView());
    ButterKnife.bind(this);

    // Create toolbar
    if (toolbar != null) {
      setSupportActionBar(toolbar);
      configureToolbar();
    }
  }

  /**
   * @return the application
   */
  public RssReaderApplication getRssReaderApplication() {
    return application;
  }

  /**
   * Configures the toolbar
   */
  protected void configureToolbar() {
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    getSupportActionBar().setHomeButtonEnabled(true);
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem pItem) {
    int id = pItem.getItemId();
    switch (id) {
      case android.R.id.home:
        Intent upIntent = NavUtils.getParentActivityIntent(this);
        if (upIntent == null) {
          upIntent = new Intent(this, MainActivity.class);
        }
        if (NavUtils.shouldUpRecreateTask(this, upIntent)) {
          TaskStackBuilder.create(this)
                  .addNextIntent(upIntent)
                  .startActivities();
          finish();
        }
        else {
          NavUtils.navigateUpTo(this, upIntent);
        }
    }
    return super.onOptionsItemSelected(pItem);
  }

  /**
   *
   * @return
   */
  public DataHelper getDataHelper() {
    if (dataHelper == null) {
      dataHelper = new DataHelperImpl(this);
    }
    return dataHelper;
  }

  /**
   *
   * @return the contentview to use
   */
  protected abstract int getContentView();
}
