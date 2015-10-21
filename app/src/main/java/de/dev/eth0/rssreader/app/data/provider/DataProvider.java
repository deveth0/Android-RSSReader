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

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.net.Uri;
import android.util.SparseArray;
import de.dev.eth0.rssreader.app.data.database.DatabaseHelper;
import java.util.Arrays;
import timber.log.Timber;

/**
 *
 * @author amuthmann
 */
public class DataProvider extends ContentProvider {

  private DatabaseHelper databaseHelper;
  private static final UriMatcher URI_MATCHER;
  private static final SparseArray<String> MIME_TYPES;

  static {
    URI_MATCHER = new UriMatcher(0);
    MIME_TYPES = new SparseArray<>();
    for (DataProviderConfiguration config : DataProviderConfiguration.values()) {
      URI_MATCHER.addURI(
              DataProviderConfiguration.AUTHORITY,
              config.getPath(),
              config.getCode());
      MIME_TYPES.put(config.getCode(), config.getMimeType());
    }

  }

  @Override
  public boolean onCreate() {
    databaseHelper = new DatabaseHelper(getContext());
    return true;
  }

  @Override
  public Cursor query(
          Uri uri,
          String[] projection,
          String selection,
          String[] selectionArgs,
          String sortOrder) {
    Timber.d("query %s with projection %s, selection %s and sortOrder %s", uri.toString(), Arrays.toString(projection), selection, sortOrder);
    SQLiteDatabase db = databaseHelper.getReadableDatabase();
    // Decodes the content URI and maps it to a code
    Cursor returnCursor;
    DataProviderConfiguration config = DataProviderConfiguration.valueOf(URI_MATCHER.match(uri));
    switch (config) {
      default:
        // Does the query against a read-only version of the database
        returnCursor = db.query(
                config.getPath(),
                projection,
                selection, selectionArgs, null, null, sortOrder);
        break;
    }
    // Sets the ContentResolver to watch this content URI for data changes
    if (returnCursor != null) {
      returnCursor.setNotificationUri(getContext().getContentResolver(), uri);
    }
    return returnCursor;
  }

  @Override
  public String getType(Uri uri) {
    return MIME_TYPES.get(URI_MATCHER.match(uri));
  }

  @Override
  public Uri insert(Uri uri, ContentValues values) {

    Timber.d("insert " + uri.toString() + " values: " + values.toString());
    SQLiteDatabase db = databaseHelper.getWritableDatabase();
    Long id = db.insert(DataProviderConfiguration.valueOf(URI_MATCHER.match(uri)).getPath(), null, values);
    if (-1 != id) {
      getContext().getContentResolver().notifyChange(uri, null);
      return Uri.withAppendedPath(uri, Long.toString(id));
    }
    else {
      throw new SQLiteException("Insert error:" + uri);
    }
  }

  @Override
  public int delete(Uri uri, String selection, String[] selectionArgs) {
    Timber.d("delete " + uri.toString() + " selection " + selection + ": " + Arrays.toString(selectionArgs));
//    switch (DataProviderConfiguration.valueOf(URI_MATCHER.match(uri))) {
//      case STORE_TABLE:
//        SQLiteDatabase db = databaseHelper.getWritableDatabase();
//        int rows = db.delete(StoreTable.TABLE_NAME, selection, selectionArgs);
//        if (-1 != rows) {
//          getContext().getContentResolver().notifyChange(uri, null);
//          return rows;
//        }
//    }
    return -1;
  }

  @Override
  public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
    Timber.d("update " + uri.toString() + " values: " + (values != null ? values.toString() : ""));
    // Decodes the content URI and maps it to a code
    SQLiteDatabase db = databaseHelper.getWritableDatabase();
    int rows;
    switch (DataProviderConfiguration.valueOf(URI_MATCHER.match(uri))) {
      default:
        rows = db.update(DataProviderConfiguration.valueOf(URI_MATCHER.match(uri)).getPath(), values, selection, selectionArgs);
    }
    if (0 != rows) {
      getContext().getContentResolver().notifyChange(uri, null);
    }
    return rows;
  }

  /**
   * A test package can call this to get a handle to the database underlying, so it can insert test data into the database. The test case class is responsible
   * for instantiating the provider in a test context; {@link android.test.ProviderTestCase2} does this during the call to setUp()
   *
   * @return a handle to the database helper object for the provider's data.
   */
  public DatabaseHelper getDatabaseHelper() {
    return databaseHelper;
  }
}
