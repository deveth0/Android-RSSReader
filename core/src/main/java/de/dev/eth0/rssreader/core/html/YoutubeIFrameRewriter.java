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
package de.dev.eth0.rssreader.core.html;

import de.dev.eth0.rssreader.core.Constants;
import java.util.regex.Matcher;
import timber.log.Timber;

public class YoutubeIFrameRewriter implements MarkupRewriter {

  @Override
  public String rewrite(String content) {
    //TODO: build one pattern whcih matches all :)
    Matcher matcher = Constants.PATTERN_IFRAMES.matcher(content);
    while (matcher.find()) {
      String find = matcher.group(0);
      String url = matcher.group(2);
      Matcher idMatcher = Constants.PATTERN_YOUTUBE.matcher(url);
      if (idMatcher.matches() && !matcher.group(1).isEmpty()) {
        String imgUrl = String.format(Constants.YOUTUBE_PREVIEW_URL, idMatcher.group(1));
        //TODO: also allow //youtube.com urls
        String replacement = String.format("<a href=\"%s\"><img src=\"%s\"/></a>", url, imgUrl);
        Timber.d("Rewriting iFrame %s to %s)", find, replacement);
        content = content.replace(find, replacement);
      }
    }
    return content;
  }
}
