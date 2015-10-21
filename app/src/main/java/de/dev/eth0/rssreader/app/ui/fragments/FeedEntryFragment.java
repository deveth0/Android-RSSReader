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

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import de.dev.eth0.rssreader.R;
import de.dev.eth0.rssreader.app.data.database.tables.FeedEntryTable;
import de.dev.eth0.rssreader.app.util.html.rewriter.HtmlRewriter;
import de.dev.eth0.rssreader.core.data.model.FeedEntry;
import de.dev.eth0.rssreader.core.data.model.FeedEntryMetadata;
import timber.log.Timber;

/**
 *
 * @author deveth0
 */
public class FeedEntryFragment extends AbstractBaseFragment {

  @Bind(R.id.feedentry_content)
  protected TextView textViewContent;
  @Bind(R.id.feedentry_title)
  @Nullable
  protected TextView textViewTitle;
  @Bind(R.id.feedentry_author)
  @Nullable
  protected TextView textViewAuthor;

  protected MenuItem menuBookmarked;

  private long feedId = Long.MIN_VALUE;
  private FeedEntry feedEntry;

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.feed_entry_fragment, container, false);
    ButterKnife.bind(this, view);
    setHasOptionsMenu(true);
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
    // restore from saved instance if possible
    if (savedInstanceState != null && savedInstanceState.containsKey(FeedEntryTable.COLUMN_ID)) {
      feedId = savedInstanceState.getLong(FeedEntryTable.COLUMN_ID);
    }
    else {
      Bundle args = getArguments();
      if (args != null && args.containsKey(FeedEntryTable.COLUMN_ID)) {
        feedId = args.getLong(FeedEntryTable.COLUMN_ID);
      }
    }
    //only display something if an id has been found
    if (feedId != Long.MIN_VALUE) {
      displayFeedEntry(feedId);
    }
  }

  @Override
  public void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(outState);
    outState.putLong(FeedEntryTable.COLUMN_ID, feedId);
  }

  @Override
  public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
    inflater.inflate(R.menu.feed_entry_activity_menu, menu);
    menuBookmarked = menu.findItem(R.id.menu_bookmark);
    if (feedEntry != null) {
      setIcon();
    }
    super.onCreateOptionsMenu(menu, inflater);
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    // Handle presses on the action bar items
    Intent i;
    FeedEntryMetadata metadata;
    switch (item.getItemId()) {
      case R.id.menu_open_browser:
        metadata = getDataProviderHelper().getFeedEntryMetadata(feedId);
        if (metadata != null) {
          i = new Intent(Intent.ACTION_VIEW);
          i.setData(Uri.parse(metadata.getLink()));
          startActivity(i);
          return true;
        }
        break;
      case R.id.menu_share:
        metadata = getDataProviderHelper().getFeedEntryMetadata(feedId);
        if (metadata != null) {
          i = new Intent(android.content.Intent.ACTION_SEND);
          i.setType("text/plain");
          //TODO: test this on older devices!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
          if (android.os.Build.VERSION.SDK_INT >= 21) {
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_DOCUMENT);
          }
          else {
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
          }
          i.putExtra(Intent.EXTRA_SUBJECT, metadata.getTitle());
          i.putExtra(Intent.EXTRA_TEXT, metadata.getLink());
          //TODO: translate
          startActivity(Intent.createChooser(i, "LALALA FOOBAR Translate me"));
          return true;
        }
        break;
      case R.id.menu_bookmark:
        Timber.d("Toggling bookmark status");
        feedEntry.getMetadata().setBookmarked(!feedEntry.getMetadata().isBookmarked());
        getDataProviderHelper().updateFeedEntryBookmarked(feedEntry.getMetadata());
        setIcon();
        return true;
    }
    return super.onOptionsItemSelected(item);
  }

  /**
   * Displays the given feed
   *
   * @param id
   */
  public void displayFeedEntry(Long id) {
    Timber.d("Display feed with id %d", id);
    if (isAdded()) {
      Timber.d("Loading entry %d", id);
      feedId = id;
      feedEntry = getDataProviderHelper().getFeedEntry(id);
      if (feedEntry == null) {
        //fallback to newest summary if possible
        Timber.d("Fallback to newest summary");
        feedEntry = getDataProviderHelper().getFeedEntry(null);
      }
      if (feedEntry != null) {
        Timber.d("Loaded summary");
        if (textViewTitle != null) {
          textViewTitle.setText(feedEntry.getMetadata().getTitle());
        }
        if (textViewAuthor != null) {
          textViewAuthor.setText(feedEntry.getMetadata().getCreator());
        }
        HtmlRewriter.setTextAndRewrite(textViewContent, getActivity(), feedEntry.getContent());
        setIcon();
      }
    }
  }

  private void setIcon() {
    if (menuBookmarked != null) {
      menuBookmarked.setIcon(feedEntry.getMetadata().isBookmarked()
              ? R.drawable.ic_action_bookmark
              : R.drawable.ic_action_bookmark_outline);
    }
  }

}
