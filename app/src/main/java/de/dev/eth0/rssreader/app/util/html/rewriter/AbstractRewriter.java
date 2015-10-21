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
package de.dev.eth0.rssreader.app.util.html.rewriter;

import android.text.SpannableStringBuilder;
import android.text.style.URLSpan;
import timber.log.Timber;

public abstract class AbstractRewriter implements HtmlRewriter.Rewriter {

  @Override
  public void rewrite(SpannableStringBuilder strBuilder) {
    URLSpan[] urls = strBuilder.getSpans(0, strBuilder.length(), URLSpan.class);
    for (final URLSpan span : urls) {
      Timber.d("Rewriting url %s", span.getURL());
      if (accepts(span.getURL())) {
        Object rewritten = rewrite(span.getURL());
        if (rewritten != null) {
          int start = strBuilder.getSpanStart(span);
          int end = strBuilder.getSpanEnd(span);
          int flags = strBuilder.getSpanFlags(span);
          strBuilder.setSpan(rewritten, start, end, flags);
          strBuilder.removeSpan(span);
        }
      }
    }
  }

  public abstract Object rewrite(String url);

  public abstract boolean accepts(String url);
}
