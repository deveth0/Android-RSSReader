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
package de.dev.eth0.rssreader.core.http.precacher;

import de.dev.eth0.rssreader.core.data.model.FeedEntry;
import de.dev.eth0.rssreader.core.html.MarkupRewriter;
import java.util.List;

public abstract class AbstractPrecacheHelper implements PrecacheHelper {

  @Override
  public void precache(List<FeedEntry> entries) {
    for (FeedEntry entry : entries) {
      String content = entry.getContent();
      for (MarkupRewriter rewriter : getMarkupRewriter()) {
        content = rewriter.rewrite(content);
      }
      for (EntryPrecacher cacher : getPrecacher()) {
        cacher.precache(content);
      }
      List<String> urls = parseUrls(content);
      for (PrecacheHelper.UrlPrecacher cacher : getUrlPrecacher()) {
        cacher.precache(urls);
      }
    }
  }

  protected abstract List<MarkupRewriter> getMarkupRewriter();

  protected abstract List<EntryPrecacher> getPrecacher();

  protected abstract List<PrecacheHelper.UrlPrecacher> getUrlPrecacher();

  protected abstract List<String> parseUrls(String content);

}
