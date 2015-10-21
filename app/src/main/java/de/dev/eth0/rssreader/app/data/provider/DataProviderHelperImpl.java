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
package de.dev.eth0.rssreader.app.data.provider;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import de.dev.eth0.rssreader.app.data.database.tables.FeedEntryTable;
import de.dev.eth0.rssreader.core.data.model.Author;
import de.dev.eth0.rssreader.core.data.model.FeedEntry;
import de.dev.eth0.rssreader.core.data.model.FeedEntryMetadata;
import de.dev.eth0.rssreader.core.data.parser.AuthorJsonParser;
import de.dev.eth0.rssreader.core.data.provider.DataProviderHelper;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import timber.log.Timber;

public class DataProviderHelperImpl implements DataProviderHelper {

  private final ContentResolver resolver;
  private final Context context;
  private static List<Author> authors;

  public static List<FeedEntryMetadata> summaries = new ArrayList<>();

  public DataProviderHelperImpl(Context context) {
    this(context, context.getContentResolver());
  }

  public DataProviderHelperImpl(Context context, ContentResolver resolver) {
    this.context = context;
    this.resolver = resolver;
  }

  @Override
  public List<FeedEntryMetadata> getFeedEntryMetadatas() {
    List<FeedEntryMetadata> ret = new ArrayList<>();
    Cursor cursor = resolver.query(DataProviderConfiguration.FEED_ENTRY_TABLE.getUri(),
            FeedEntryTable.PROJECTION_METADATA,
            null,
            null,
            FeedEntryTable.COLUMN_ID + " DESC");
    if (cursor == null) {
      return ret;
    }
    while (cursor.moveToNext()) {
      ret.add(FeedEntryTable.fromCursorFeedEntryMetadata(cursor));
    }
    cursor.close();
    return ret;
  }

  @Override
  public List<FeedEntryMetadata> getBookmarkedFeedEntryMetadatas() {
    List<FeedEntryMetadata> ret = new ArrayList<>();
    Cursor cursor = resolver.query(DataProviderConfiguration.FEED_ENTRY_TABLE.getUri(),
            FeedEntryTable.PROJECTION_METADATA,
            FeedEntryTable.TABLE_NAME + "." + FeedEntryTable.COLUMN_BOOKMARKED + "='1'",
            null,
            FeedEntryTable.COLUMN_ID + " DESC");
    if (cursor == null) {
      return ret;
    }
    while (cursor.moveToNext()) {
      ret.add(FeedEntryTable.fromCursorFeedEntryMetadata(cursor));
    }
    cursor.close();
    return ret;
  }

  @Override
  public List<FeedEntry> insertFeedEntry(List<FeedEntry> summaries) {
    List<FeedEntry> updated = new ArrayList<>();
    for (FeedEntry summary : summaries) {
      ContentValues value = FeedEntryTable.getContentValues(summary);
      if (resolver.update(DataProviderConfiguration.FEED_ENTRY_TABLE.getUri(), value, FeedEntryTable.COLUMN_ID + "=?",
              new String[]{summary.getMetadata().getId().toString()}) == 0) {
        resolver.insert(DataProviderConfiguration.FEED_ENTRY_TABLE.getUri(), FeedEntryTable.getContentValues(summary));
        updated.add(summary);
      }
    }
    return updated;
  }

  @Override
  public FeedEntry getFeedEntry(Long id) {
    Cursor cursor;
    if (id != null) {
      cursor = resolver.query(DataProviderConfiguration.FEED_ENTRY_TABLE.getUri(),
              FeedEntryTable.PROJECTION,
              FeedEntryTable.TABLE_NAME + "." + FeedEntryTable.COLUMN_ID + "='" + id + "'",
              null,
              FeedEntryTable.TABLE_NAME + "." + FeedEntryTable.COLUMN_ID + " LIMIT 1");
    }
    else {
      cursor = resolver.query(DataProviderConfiguration.FEED_ENTRY_TABLE.getUri(),
              FeedEntryTable.PROJECTION,
              null,
              null,
              FeedEntryTable.TABLE_NAME + "." + FeedEntryTable.COLUMN_ID + " DESC LIMIT 1");
    }
    if (cursor.moveToNext()) {
      FeedEntry ret = FeedEntryTable.fromCursorFeedEntry(cursor);
      cursor.close();
      return ret;
    }
    return null;
  }

  @Override
  public FeedEntryMetadata getFeedEntryMetadata(long id) {
    Cursor cursor = resolver.query(DataProviderConfiguration.FEED_ENTRY_TABLE.getUri(),
            FeedEntryTable.PROJECTION_METADATA,
            FeedEntryTable.TABLE_NAME + "." + FeedEntryTable.COLUMN_ID + "='" + id + "'",
            null,
            FeedEntryTable.TABLE_NAME + "." + FeedEntryTable.COLUMN_ID + " LIMIT 1");
    if (cursor.moveToNext()) {
      FeedEntryMetadata ret = FeedEntryTable.fromCursorFeedEntryMetadata(cursor);
      cursor.close();
      return ret;
    }
    return null;
  }

  @Override
  public FeedEntryMetadata getFeedEntryMetadataForUrl(String url) {
    Cursor cursor = resolver.query(DataProviderConfiguration.FEED_ENTRY_TABLE.getUri(),
            FeedEntryTable.PROJECTION_METADATA,
            FeedEntryTable.TABLE_NAME + "." + FeedEntryTable.COLUMN_LINK + "='" + url + "'",
            null,
            FeedEntryTable.TABLE_NAME + "." + FeedEntryTable.COLUMN_ID + " LIMIT 1");
    if (cursor.moveToNext()) {
      FeedEntryMetadata ret = FeedEntryTable.fromCursorFeedEntryMetadata(cursor);
      cursor.close();
      return ret;
    }
    return null;
  }

  @Override
  public long getFeedEntryIdForPosition(int i) {
    Cursor cursor = resolver.query(DataProviderConfiguration.FEED_ENTRY_TABLE.getUri(),
            new String[]{FeedEntryTable.COLUMN_ID},
            null,
            null,
            FeedEntryTable.TABLE_NAME + "." + FeedEntryTable.COLUMN_ID + " DESC");
    if (cursor.moveToPosition(i)) {
      Long ret = cursor.getLong(cursor.getColumnIndex(FeedEntryTable.COLUMN_ID));
      cursor.close();
      return ret;
    }
    return -1;
  }

  @Override
  public int getFeedEntryCount() {
    Cursor cursor = resolver.query(DataProviderConfiguration.FEED_ENTRY_TABLE.getUri(),
            new String[]{FeedEntryTable.COLUMN_ID},
            null,
            null,
            null);
    int ret = cursor.getCount();
    cursor.close();
    return ret;
  }

  @Override
  public int getFeedEntryPositionForId(long feedId) {
    //TODO: change this, it's not a good idea to iterate over all items to find the correct one. There should be an auto-increment PK used for this
    Cursor cursor = resolver.query(DataProviderConfiguration.FEED_ENTRY_TABLE.getUri(),
            new String[]{FeedEntryTable.COLUMN_ID},
            null,
            null,
            FeedEntryTable.TABLE_NAME + "." + FeedEntryTable.COLUMN_ID + " DESC");
    int i = 0;
    while (cursor.moveToNext()) {
      Long id = cursor.getLong(cursor.getColumnIndex(FeedEntryTable.COLUMN_ID));
      Timber.d("Found id %d (looking for %d)", id, feedId);
      if (id == feedId) {
        break;
      }
      i++;
    }
    Timber.d("Item with id %d has position %d", feedId, i);
    cursor.close();
    return i;
  }

  @Override
  public void updateFeedEntryBookmarked(FeedEntryMetadata metadata) {
    ContentValues values = new ContentValues();
    values.put(FeedEntryTable.COLUMN_ID, metadata.getId());
    values.put(FeedEntryTable.COLUMN_BOOKMARKED, metadata.isBookmarked());
    resolver.update(DataProviderConfiguration.FEED_ENTRY_TABLE.getUri(), values, FeedEntryTable.COLUMN_ID + "=?",
            new String[]{metadata.getId().toString()});
  }

  @Override
  public synchronized List<Author> getAuthors() {
    if (authors == null) {
      try {
        authors = AuthorJsonParser.parseAuthors(context.getAssets().open("authors.json"));
      }
      catch (IOException ex) {
        Timber.d(ex, "Could not load authors");
        authors = new ArrayList<>();
      }
    }
    return authors;
  }

}
