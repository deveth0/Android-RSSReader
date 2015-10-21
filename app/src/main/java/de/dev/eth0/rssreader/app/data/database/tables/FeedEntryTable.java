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
package de.dev.eth0.rssreader.app.data.database.tables;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import de.dev.eth0.rssreader.core.data.model.FeedEntry;
import de.dev.eth0.rssreader.core.data.model.FeedEntryMetadata;
import de.dev.eth0.rssreader.core.data.model.impl.FeedEntryImpl;
import de.dev.eth0.rssreader.core.data.model.impl.FeedEntryMetadataImpl;
import timber.log.Timber;

public class FeedEntryTable extends AbstractDatabaseTable {

  /**
   * table name
   */
  public static final String TABLE_NAME = "feedentries";
  public static final String COLUMN_TITLE = "feedETitle";
  public static final String COLUMN_CATEGORIES = "feedEntryCategories";
  public static final String COLUMN_DESCRIPTION = "feedEntryDescription";
  public static final String COLUMN_CONTENT = "feedEntryContent";
  public static final String COLUMN_LINK = "feedEntryLink";
  public static final String COLUMN_COMMENTS_LINK = "feedEntryCommentsLink";
  public static final String COLUMN_CREATOR = "feedEntryCreator";
  public static final String COLUMN_BOOKMARKED = "feedEntryBookmarked";

  public static final String[] PROJECTION_METADATA = {
    COLUMN_ID, COLUMN_TITLE, COLUMN_CATEGORIES, COLUMN_DESCRIPTION, COLUMN_LINK, COLUMN_COMMENTS_LINK, COLUMN_CREATOR, COLUMN_BOOKMARKED
  };
  public static final String[] PROJECTION = {
    COLUMN_ID, COLUMN_TITLE, COLUMN_CATEGORIES, COLUMN_DESCRIPTION, COLUMN_CONTENT, COLUMN_LINK, COLUMN_COMMENTS_LINK, COLUMN_CREATOR, COLUMN_BOOKMARKED
  };

  private static final String DATABASE_CREATE = "create table if not exists "
          + TABLE_NAME
          + "("
          + COLUMN_ID + " integer primary key unique, "
          + COLUMN_TITLE + " text not null, "
          + COLUMN_CATEGORIES + " text not null, "
          + COLUMN_DESCRIPTION + " text not null, "
          + COLUMN_CONTENT + " text not null, "
          + COLUMN_COMMENTS_LINK + " text, "
          + COLUMN_LINK + " text, "
          + COLUMN_BOOKMARKED + " integer default 0, "
          + COLUMN_CREATOR + " text not null "
          + ");";

  /**
   *
   * @param pDatabase
   */
  public static void onCreate(SQLiteDatabase pDatabase) {
    Timber.i("Creating database.");
    pDatabase.execSQL(DATABASE_CREATE);
  }

  /**
   * @param pDatabase
   * @param pNewVersion
   * @param pOldVersion
   */
  public static void onUpgrade(SQLiteDatabase pDatabase, int pOldVersion, int pNewVersion) {
    // ensure database exists
    Timber.i("onUpgrade");
    pDatabase.execSQL("DROP table if exists " + TABLE_NAME + ";");
    pDatabase.execSQL(DATABASE_CREATE);
    // do nothing
  }

  /**
   * @param summary
   * @return
   */
  public static ContentValues getContentValues(FeedEntry summary) {
    ContentValues content = new ContentValues();
    FeedEntryMetadata metadata = summary.getMetadata();
    content.put(COLUMN_ID, metadata.getId());
    content.put(COLUMN_TITLE, metadata.getTitle());
    content.put(COLUMN_DESCRIPTION, metadata.getDescription());
    content.put(COLUMN_CREATOR, metadata.getCreator());
    content.put(COLUMN_LINK, metadata.getLink());
    content.put(COLUMN_COMMENTS_LINK, metadata.getCommentsLink());
    content.put(COLUMN_CATEGORIES, metadata.getCategories());
    content.put(COLUMN_BOOKMARKED, metadata.isBookmarked());
    content.put(COLUMN_CONTENT, summary.getContent());
    return content;
  }

  /**
   *
   * @param cursor
   * @return
   */
  public static FeedEntryMetadata fromCursorFeedEntryMetadata(Cursor cursor) {
    long date = cursor.getLong(cursor.getColumnIndex(FeedEntryTable.COLUMN_ID));
    String title = cursor.getString(cursor.getColumnIndex(FeedEntryTable.COLUMN_TITLE));
    String description = cursor.getString(cursor.getColumnIndex(FeedEntryTable.COLUMN_DESCRIPTION));
    String creator = cursor.getString(cursor.getColumnIndex(FeedEntryTable.COLUMN_CREATOR));
    String link = cursor.getString(cursor.getColumnIndex(FeedEntryTable.COLUMN_LINK));
    String commentslink = cursor.getString(cursor.getColumnIndex(FeedEntryTable.COLUMN_COMMENTS_LINK));
    String categories = cursor.getString(cursor.getColumnIndex(FeedEntryTable.COLUMN_CATEGORIES));
    boolean bookmarked = cursor.getInt(cursor.getColumnIndex(FeedEntryTable.COLUMN_BOOKMARKED)) == 1;
    return new FeedEntryMetadataImpl(date, title, description, creator, link, commentslink, categories, bookmarked);
  }

  public static FeedEntry fromCursorFeedEntry(Cursor cursor) {
    FeedEntryMetadata metadata = fromCursorFeedEntryMetadata(cursor);
    String content = cursor.getString(cursor.getColumnIndex(FeedEntryTable.COLUMN_CONTENT));
    return new FeedEntryImpl(metadata, content);
  }
}
