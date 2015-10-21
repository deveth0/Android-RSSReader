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
package de.dev.eth0.rssreader.core.data.model;

/**
 * Metadata for a feedentry
 *
 * @author amuthmann
 */
public interface FeedEntryMetadata {

  /**
   *
   * @return
   */
  Long getId();

  /**
   *
   * @param id
   */
  void setId(Long id);

  /**
   * Get the value of title
   *
   * @return the value of title
   */
  String getTitle();

  /**
   *
   * @return all categories (comma-separated)
   */
  String getCategories();
  /**
   *
   * @param categories
   */
  void setCategories(String categories);

  /**
   *
   * @return link
   */
  String getLink();

  /**
   *
   * @return link to comments
   */
  String getCommentsLink();

  /**
   *
   * @return description text
   */
  String getDescription();

  String getCreator();

  boolean isBookmarked();

  void setBookmarked(boolean isBookmarked);

  void setTitle(String title);

  void setLink(String link);

  void setCommentsLink(String commentslink);

  void setDescription(String description);

  void setCreator(String creator);

  boolean isValid();

}
