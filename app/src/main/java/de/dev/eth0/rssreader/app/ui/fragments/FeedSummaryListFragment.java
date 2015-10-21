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

import android.database.ContentObserver;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.squareup.okhttp.Request;
import de.dev.eth0.rssreader.R;
import de.dev.eth0.rssreader.app.adapter.FeedListAdapter;
import de.dev.eth0.rssreader.app.adapter.FeedListHolder;
import de.dev.eth0.rssreader.app.data.UpdateViewContentObserver;
import de.dev.eth0.rssreader.app.data.provider.DataProviderConfiguration;
import de.dev.eth0.rssreader.app.http.HttpLoaderImpl;
import de.dev.eth0.rssreader.app.http.callback.FeedLoaderCallbackImpl;
import de.dev.eth0.rssreader.app.ui.view.DividerItemDecoration;
import de.dev.eth0.rssreader.app.util.ActivityStarterHelper;
import de.dev.eth0.rssreader.core.data.model.FeedEntryMetadata;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author deveth0
 */
public class FeedSummaryListFragment extends AbstractBaseFragment implements UpdateableFragment, SwipeRefreshLayout.OnRefreshListener {

  private FeedListAdapter adapter;

  @Bind(R.id.feed_summary_refresh_layout)
  protected SwipeRefreshLayout swipelayout;

  @Bind(R.id.feed_summary_list)
  protected RecyclerView recList;

  private final ContentObserver contentObserver = new UpdateViewContentObserver(new Handler(Looper.getMainLooper()), this);

  @Override
  public void onPause() {
    super.onPause();
    unregisterContentObserver(contentObserver);
  }

  @Override
  public void onResume() {
    super.onResume();
    registerContentObserver(DataProviderConfiguration.FEED_ENTRY_TABLE.getUri(), true, contentObserver);
    onUpdate();
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.feed_summary_list_fragment, container, false);
    ButterKnife.bind(this, view);
    RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST);
    recList.addItemDecoration(itemDecoration);
    return view;
  }

  @Override
  public void onDestroyView() {
    super.onDestroyView();
    ButterKnife.unbind(this);
  }

  @Override
  public void onViewCreated(View view, Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    recList.setHasFixedSize(true);
    LinearLayoutManager llm = new LinearLayoutManager(getActivity());
    llm.setOrientation(LinearLayoutManager.VERTICAL);
    recList.setLayoutManager(llm);

    View.OnClickListener listener = new View.OnClickListener() {

      @Override
      public void onClick(View v) {
        FeedListHolder holder = (FeedListHolder)recList.getChildViewHolder(v);
        ActivityStarterHelper.displayFeedEntry(getActivity(), holder.getEntry().getId());
      }
    };

    adapter = new FeedListAdapter(new ArrayList<FeedEntryMetadata>(), getBaseActivity(), listener);
    recList.setAdapter(adapter);
    swipelayout.setOnRefreshListener(this);
  }

  @Override
  public void onUpdate() {
    adapter.updateContent(getEntries());
    adapter.notifyDataSetChanged();
  }

  @Override
  public void onRefresh() {
    FeedLoaderCallbackImpl callback = new FeedLoaderCallbackImpl(getActivity()) {

      @Override
      public void onFailure(Request request, IOException ioe) {
        new Handler(Looper.getMainLooper()).post(new Runnable() {

          @Override
          public void run() {
            Toast.makeText(getActivity(), "Da lief wohl etwas schief...", Toast.LENGTH_SHORT).show();
          }
        });
        super.onFailure(request, ioe);
      }

      @Override
      public void afterCallback() {
        new Handler(Looper.getMainLooper()).post(new Runnable() {

          @Override
          public void run() {
            swipelayout.setRefreshing(false);
          }
        });
      }

    };
    HttpLoaderImpl.getInstance(getActivity()).loadFeedSummary(callback);
  }

  protected List<FeedEntryMetadata> getEntries() {
    return getDataProviderHelper().getFeedEntryMetadatas();
  }

}
