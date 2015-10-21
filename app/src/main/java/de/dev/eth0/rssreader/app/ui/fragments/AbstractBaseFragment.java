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
package de.dev.eth0.rssreader.app.ui.fragments;

import android.app.Activity;
import android.database.ContentObserver;
import android.net.Uri;
import android.support.v4.app.Fragment;
import de.dev.eth0.rssreader.app.RssReaderApplication;
import de.dev.eth0.rssreader.app.data.provider.DataProviderHelperImpl;
import de.dev.eth0.rssreader.app.ui.AbstractBaseActivity;
import de.dev.eth0.rssreader.core.data.provider.DataProviderHelper;

/**
 *
 * @author amuthmann
 */
public abstract class AbstractBaseFragment extends Fragment {

  private AbstractBaseActivity activity;
  private RssReaderApplication application;

  private DataProviderHelper dataProviderHelper;

  @Override
  public void onAttach(Activity activity) {
    super.onAttach(activity);
    this.activity = (AbstractBaseActivity)activity;
    this.application = this.activity.getRssReaderApplication();
  }

  public AbstractBaseActivity getBaseActivity() {
    return activity;
  }

  /**
   *
   * @return
   */
  public DataProviderHelper getDataProviderHelper() {
    if (dataProviderHelper == null) {
      dataProviderHelper = new DataProviderHelperImpl(getActivity());
    }
    return dataProviderHelper;
  }

  /**
   *
   * @return the RssReaderApplication
   */
  public RssReaderApplication getRssReaderApplication() {
    return application;
  }

  protected void unregisterContentObserver(ContentObserver observer) {
    getActivity().getContentResolver().unregisterContentObserver(observer);
  }

  protected void registerContentObserver(Uri uri, boolean b, ContentObserver contentObserver) {
    getActivity().getContentResolver().registerContentObserver(uri, b, contentObserver);
  }

}
