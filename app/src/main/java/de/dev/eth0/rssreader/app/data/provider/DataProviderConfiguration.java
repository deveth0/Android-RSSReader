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

import android.net.Uri;
import de.dev.eth0.rssreader.app.data.database.tables.FeedEntryTable;

/**
 * All dataprovider configurations
 *
 * @author amuthmann
 */
public enum DataProviderConfiguration {

  /**
   *
   */
  FEED_ENTRY_TABLE(FeedEntryTable.TABLE_NAME, 0);

  private final Uri uri;
  private final String path;
  private final String mimeType;
  private final int code;

  private DataProviderConfiguration(String path, int code) {
    //this.uri = Uri.withAppendedPath(DataProviderConfiguration.CONTENT_URI, name);
    this.uri = Uri.withAppendedPath(Uri.parse(SCHEME + "://" + AUTHORITY), path);
    this.code = code;
    this.path = path;
    this.mimeType = MIME_BASE + path;
  }

  /**
   *
   * @return
   */
  public Uri getUri() {
    return uri;
  }

  /**
   *
   * @return
   */
  public String getPath() {
    return path;
  }

  /**
   *
   * @return
   */
  public int getCode() {
    return code;
  }

  /**
   *
   * @return
   */
  public String getMimeType() {
    return mimeType;
  }

  /**
   *
   * @param code
   * @return
   */
  public static DataProviderConfiguration valueOf(int code) {
    for (DataProviderConfiguration config : values()) {
      if (config.getCode() == code) {
        return config;
      }
    }
    return null;
  }

  /**
   * The URI scheme used for content URIs
   */
  public static final String SCHEME = "content";

  /**
   * The provider's authority
   */
  public static final String AUTHORITY = "de.dev.eth0.rssreader";
  /**
   *
   */
  public static final Uri CONTENT_URI = Uri.parse(SCHEME + "://" + AUTHORITY);
  /**
   *
   */
  private static final String MIME_BASE = "vnd.android.cursor.dir/vnd." + DataProviderConfiguration.AUTHORITY + ".";

}
