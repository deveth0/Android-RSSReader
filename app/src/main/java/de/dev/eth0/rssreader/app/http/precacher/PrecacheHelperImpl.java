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
package de.dev.eth0.rssreader.app.http.precacher;

import android.content.Context;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.URLSpan;
import de.dev.eth0.rssreader.app.http.HttpLoaderImpl;
import de.dev.eth0.rssreader.app.util.PreferenceHelper;
import de.dev.eth0.rssreader.core.html.MarkupRewriter;
import de.dev.eth0.rssreader.core.html.YoutubeIFrameRewriter;
import de.dev.eth0.rssreader.core.http.precacher.AbstractPrecacheHelper;
import de.dev.eth0.rssreader.core.http.precacher.impl.DownloadPrecacherImpl;
import java.util.ArrayList;
import java.util.List;

public class PrecacheHelperImpl extends AbstractPrecacheHelper {

  private final Context context;
  private List<EntryPrecacher> entryPrecacher;
  private List<UrlPrecacher> urlPrecacher;
  private List<MarkupRewriter> markupRewriter;

  public PrecacheHelperImpl(Context context) {
    this.context = context;
  }

  @Override
  protected List<EntryPrecacher> getPrecacher() {
    if (entryPrecacher == null) {
      entryPrecacher = new ArrayList<>();
      if (PreferenceHelper.isPrecacheImagesEnabled(context)) {
        entryPrecacher.add(new InlineImagePrecacherImpl(context, HttpLoaderImpl.getInstance(context)));
      }
    }
    return entryPrecacher;
  }

  @Override
  protected List<UrlPrecacher> getUrlPrecacher() {
    if (urlPrecacher == null) {
      urlPrecacher = new ArrayList<>();
      if (PreferenceHelper.isPrecacheImagesEnabled(context)) {
        urlPrecacher.add(new ImagePrecacherImpl(HttpLoaderImpl.getInstance(context)));
      }
      if (PreferenceHelper.isPrecachePDFEnabled(context)) {
        urlPrecacher.add(new DownloadPrecacherImpl(HttpLoaderImpl.getInstance(context)));
      }
    }
    return urlPrecacher;
  }

  @Override
  protected List<MarkupRewriter> getMarkupRewriter() {
    if (markupRewriter == null) {
      markupRewriter = new ArrayList<>();
      markupRewriter.add(new YoutubeIFrameRewriter());
    }
    return markupRewriter;
  }

  @Override
  protected List<String> parseUrls(String content) {
    List<String> ret = new ArrayList<>();
    Spanned html = Html.fromHtml(content);
    SpannableStringBuilder strBuilder = new SpannableStringBuilder(html);
    URLSpan[] urls = strBuilder.getSpans(0, html.length(), URLSpan.class);
    for (URLSpan span : urls) {
      ret.add(span.getURL());
    }
    return ret;
  }

}
