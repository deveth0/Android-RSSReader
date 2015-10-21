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
package de.dev.eth0.rssreader.app.http;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.text.Html;
import de.dev.eth0.rssreader.core.http.HttpLoader;
import java.io.IOException;
import timber.log.Timber;

public class HttpImageGetter implements Html.ImageGetter {

  private final Context context;
  private final HttpLoader loader;

  public HttpImageGetter(Context context, HttpLoader loader) {
    this.context = context;
    this.loader = loader;
  }

  @Override
  public Drawable getDrawable(final String source) {
    //TODO: remove/replace this
    if (source.contains("vgwort.de")) {
      return null;
    }
    try {
      Timber.d("Loading image for source %s", source);
      Bitmap bitmap = loader.getPicasso().load(source).get();
      BitmapDrawable drawable = new BitmapDrawable(context.getResources(), bitmap);
      //TODO: resize to max screensize
      drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
      return drawable;
    }
    catch (IOException ioe) {
      Timber.d(ioe, "Could not load image %s", source);
      return null;
    }
  }

}
