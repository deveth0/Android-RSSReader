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

import de.dev.eth0.rssreader.core.data.model.AbstractModel;
import de.dev.eth0.rssreader.core.data.model.FeedEntryMetadata;

public class FeedEntryMetadataImpl extends AbstractModel implements FeedEntryMetadata {

  private String title;
  private String creator;
  private String description;
  private String link;
  private String commentsLink;
  private String categories;
  private boolean bookmarked;

  public FeedEntryMetadataImpl(Long id) {
    super(id);
  }

  public FeedEntryMetadataImpl(Long date, String title, String description, String creator, String link, String commentslink, String categories) {
    this(date, title, description, creator, link, commentslink, categories, false);
  }

  public FeedEntryMetadataImpl(Long date, String title, String description, String creator, String link, String commentslink, String categories, boolean bookmarked) {
    super(date);
    this.title = title;
    this.description = description;
    this.creator = creator;
    this.link = link;
    this.commentsLink = commentslink;
    this.categories = categories;
    this.bookmarked = bookmarked;
  }

  /**
   * Get the value of title
   *
   * @return the value of title
   */
  @Override
  public String getTitle() {
    return title;
  }

  /**
   * Set the value of title
   *
   * @param title new value of title
   */
  @Override
  public void setTitle(String title) {
    this.title = title;
  }

  @Override
  public String getCategories() {
    return categories;
  }

  @Override
  public String getLink() {
    return link;
  }

  @Override
  public String getCommentsLink() {
    return commentsLink;
  }

  @Override
  public String getDescription() {
    return description;
  }

  @Override
  public void setLink(String link) {
    this.link = link;
  }

  @Override
  public String toString() {
    return "FeedSummaryImpl{" + "title=" + title
            + ", description=" + description != null ? description.substring(0, Math.max(description.length(), 20)) : ""
                    + ", link=" + link
                    + ", commentsLink=" + commentsLink
                    + ", categories=" + categories + '}';
  }

  @Override
  public void setCategories(String category) {
    this.categories = category;
  }

  @Override
  public void setCommentsLink(String commentslink) {
    this.commentsLink = commentslink;
  }

  @Override
  public void setDescription(String description) {
    this.description = description;
  }

  @Override
  public String getCreator() {
    return creator;
  }

  @Override
  public void setCreator(String creator) {
    this.creator = creator;
  }

  @Override
  public boolean isBookmarked() {
    return bookmarked;
  }

  @Override
  public void setBookmarked(boolean bookmarked) {
    this.bookmarked = bookmarked;
  }

  @Override
  public boolean isValid() {
    return title != null && !title.isEmpty()
            && link != null && !link.isEmpty()
            && creator != null && !creator.isEmpty()
            && getId() != null;
  }

}
