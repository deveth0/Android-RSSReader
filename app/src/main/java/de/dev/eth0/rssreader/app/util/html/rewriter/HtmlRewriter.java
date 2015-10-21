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

import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.widget.TextView;
import de.dev.eth0.rssreader.app.http.HttpImageGetter;
import de.dev.eth0.rssreader.app.http.HttpLoaderImpl;
import de.dev.eth0.rssreader.app.util.html.rewriter.impl.DownloadRewriterImpl;
import de.dev.eth0.rssreader.app.util.html.rewriter.impl.ImageRewriterImpl;
import de.dev.eth0.rssreader.app.util.html.rewriter.impl.InternalLinkRewriterImpl;
import de.dev.eth0.rssreader.core.html.MarkupRewriter;
import de.dev.eth0.rssreader.core.html.YoutubeIFrameRewriter;
import java.util.ArrayList;
import java.util.List;
import org.xml.sax.XMLReader;
import timber.log.Timber;

public class HtmlRewriter {

  private final Context context;
  private List<Rewriter> rewriter;
  private List<MarkupRewriter> markupRewriter;

  public HtmlRewriter(Context context) {
    this.context = context;
  }

  public static void setTextAndRewrite(TextView textView, Context context, String content) {
    Timber.d("setTextAndRewrite");
    textView.setText(Html.fromHtml(content));
    new HtmlRewriter.HtmlRewriterTask(textView, context, content).execute((Void)null);
    textView.setMovementMethod(LinkMovementMethod.getInstance());
  }

  public Spanned fromHtml(String content) {
    //First rewrite the html
    Timber.d("Rewriting Markup");
    for (MarkupRewriter r : getMarkupRewriter()) {
      content = r.rewrite(content);
    }

    Timber.d("Parsing content");
    Spanned ret = Html.fromHtml(content,
            new HttpImageGetter(context, HttpLoaderImpl.getInstance(context)),
            new Html.TagHandler() {

              @Override
              public void handleTag(boolean opening, String tag, Editable output, XMLReader xmlReader) {
                //do nothing...
              }
            });
    SpannableStringBuilder builder = new SpannableStringBuilder(ret);

    Timber.d("Rewriting content");
    for (Rewriter r : getRewriter()) {
      r.rewrite(builder);
    }
    return builder;
  }

  private List<Rewriter> getRewriter() {
    if (rewriter == null) {
      rewriter = new ArrayList<>();
      rewriter.add(new InternalLinkRewriterImpl(context));
      rewriter.add(new ImageRewriterImpl(context));
      rewriter.add(new DownloadRewriterImpl(context));
    }
    return rewriter;
  }

  private List<MarkupRewriter> getMarkupRewriter() {
    if (markupRewriter == null) {
      markupRewriter = new ArrayList<>();
      markupRewriter.add(new YoutubeIFrameRewriter());
    }
    return markupRewriter;
  }

  public interface Rewriter {

    public void rewrite(SpannableStringBuilder strBuilder);
  }

  public static class HtmlRewriterTask extends AsyncTask<Void, Void, Spanned> {

    private final TextView tv;
    private final Context context;
    private final String content;

    public HtmlRewriterTask(TextView tv, Context context, String content) {
      this.tv = tv;
      this.context = context;
      this.content = content;
    }

    @Override
    protected Spanned doInBackground(Void... params) {
      return new HtmlRewriter(context).fromHtml(content);
    }

    @Override
    protected void onPostExecute(final Spanned result) {
      Timber.d("onPostExecute");
      new Handler(Looper.getMainLooper()).post(new Runnable() {

        @Override
        public void run() {
          tv.setText(result);
        }
      });
    }
  };

}
