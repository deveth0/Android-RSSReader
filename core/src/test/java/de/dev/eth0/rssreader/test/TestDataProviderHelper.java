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
package de.dev.eth0.rssreader.test;

import de.dev.eth0.rssreader.core.data.model.Author;
import de.dev.eth0.rssreader.core.data.model.FeedEntry;
import de.dev.eth0.rssreader.core.data.model.FeedEntryMetadata;
import de.dev.eth0.rssreader.core.data.provider.DataProviderHelper;
import java.util.List;

public class TestDataProviderHelper implements DataProviderHelper {

  @Override
  public List<FeedEntryMetadata> getFeedEntryMetadatas() {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }

  @Override
  public List<FeedEntry> insertFeedEntry(List<FeedEntry> summaries) {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }

  @Override
  public FeedEntry getFeedEntry(Long id) {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }

  @Override
  public FeedEntryMetadata getFeedEntryMetadata(long feedId) {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }

  @Override
  public FeedEntryMetadata getFeedEntryMetadataForUrl(String url) {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }

  @Override
  public long getFeedEntryIdForPosition(int i) {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }

  @Override
  public int getFeedEntryCount() {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }

  @Override
  public int getFeedEntryPositionForId(long feedId) {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }

  @Override
  public void updateFeedEntryBookmarked(FeedEntryMetadata metadata) {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }

  @Override
  public List<FeedEntryMetadata> getBookmarkedFeedEntryMetadatas() {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }

  @Override
  public List<Author> getAuthors() {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }

}
