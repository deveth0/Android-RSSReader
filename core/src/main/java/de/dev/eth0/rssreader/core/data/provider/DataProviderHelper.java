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
//$Id: DataProviderHelper.java 80 2015-10-05 11:12:03Z deveth0 $
package de.dev.eth0.rssreader.core.data.provider;

import de.dev.eth0.rssreader.core.data.model.Author;
import de.dev.eth0.rssreader.core.data.model.FeedEntry;
import de.dev.eth0.rssreader.core.data.model.FeedEntryMetadata;
import java.util.List;

public interface DataProviderHelper {

  List<FeedEntryMetadata> getFeedEntryMetadatas();

  List<FeedEntry> insertFeedEntry(List<FeedEntry> summaries);

  /**
   *
   * @param feedId
   * @return Feedentry with given id. If id is null, newest entry is returned
   */
  FeedEntry getFeedEntry(Long feedId);

  FeedEntryMetadata getFeedEntryMetadata(long feedId);

  FeedEntryMetadata getFeedEntryMetadataForUrl(String url);

  /**
   * returns the id of the feedentry found at the given position in database (sorted by date)
   *
   * @param i
   * @return
   */
  long getFeedEntryIdForPosition(int i);

  int getFeedEntryCount();

  int getFeedEntryPositionForId(long feedId);

  void updateFeedEntryBookmarked(FeedEntryMetadata metadata);

  List<FeedEntryMetadata> getBookmarkedFeedEntryMetadatas();

  List<Author> getAuthors();
}
