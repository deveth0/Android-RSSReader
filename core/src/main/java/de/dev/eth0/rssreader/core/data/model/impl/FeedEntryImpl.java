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
package de.dev.eth0.rssreader.core.data.model.impl;

import de.dev.eth0.rssreader.core.data.model.FeedEntry;
import de.dev.eth0.rssreader.core.data.model.FeedEntryMetadata;

public class FeedEntryImpl implements FeedEntry {

  private FeedEntryMetadata metadata;
  private String content;


  public FeedEntryImpl(Long id) {
    this.metadata = new FeedEntryMetadataImpl(id);
  }

  public FeedEntryImpl(FeedEntryMetadata metadata, String content) {
    this.metadata = metadata;
    this.content = content;
  }

  public FeedEntryImpl(Long date, String title, String description, String content, String creator, String link, String commentslink, String categories) {
    this.metadata = new FeedEntryMetadataImpl(date, title, description, creator, link, commentslink, categories);
    this.content = content;
  }

  @Override
  public String getContent() {
    return content;
  }

  @Override
  public void setContent(String content) {
    this.content = content;
  }

  @Override
  public FeedEntryMetadata getMetadata() {
    return metadata;
  }

  @Override
  public void setMetadata(FeedEntryMetadata metadata) {
    this.metadata = metadata;
  }

  @Override
  public boolean isValid() {
    return content != null && !content.isEmpty() && metadata.isValid();
  }

}
