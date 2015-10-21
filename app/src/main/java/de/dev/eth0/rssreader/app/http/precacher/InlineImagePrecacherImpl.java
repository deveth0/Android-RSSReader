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
import android.text.Editable;
import android.text.Html;
import de.dev.eth0.rssreader.app.http.HttpImageGetter;
import de.dev.eth0.rssreader.core.http.HttpLoader;
import de.dev.eth0.rssreader.core.http.precacher.AbstractPrecacher;
import de.dev.eth0.rssreader.core.http.precacher.PrecacheHelper;
import org.xml.sax.XMLReader;

public class InlineImagePrecacherImpl extends AbstractPrecacher implements PrecacheHelper.EntryPrecacher {

  private final Context context;

  public InlineImagePrecacherImpl(Context context, HttpLoader loader) {
    super(loader);
    this.context = context;
  }

  @Override
  public void precache(String content) {
    // For now we use the ImageGetter to preload images...
    Html.fromHtml(content, new HttpImageGetter(context, getLoader()), new Html.TagHandler() {

      @Override
      public void handleTag(boolean opening, String tag, Editable output, XMLReader xmlReader) {

      }
    });
  }

}
