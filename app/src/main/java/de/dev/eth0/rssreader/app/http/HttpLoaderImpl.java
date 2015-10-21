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
import com.squareup.okhttp.Cache;
import com.squareup.okhttp.CacheControl;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.picasso.OkHttpDownloader;
import com.squareup.picasso.Picasso;
import de.dev.eth0.rssreader.BuildConfig;
import de.dev.eth0.rssreader.app.http.callback.FeedLoaderCallbackImpl;
import de.dev.eth0.rssreader.app.util.PreferenceHelper;
import de.dev.eth0.rssreader.core.http.HttpLoader;
import de.dev.eth0.rssreader.core.http.callback.FeedLoaderCallback;
import de.dev.eth0.rssreader.core.http.callback.PrecacheCallback;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import timber.log.Timber;

/**
 *
 */
public class HttpLoaderImpl implements HttpLoader {

  private final OkHttpClient client;
  private final Context context;

  private static HttpLoaderImpl instance;
  private static Picasso picasso;

  /**
   *
   * @param context
   * @return instance of Feedloader (singleton)
   */
  public static HttpLoaderImpl getInstance(Context context) {
    if (instance == null) {
      Timber.d("Building new instance");
      instance = new HttpLoaderImpl(context);
    }
    return instance;
  }

  private HttpLoaderImpl(Context context) {
    this.client = new OkHttpClient();
    this.context = context;
    Cache cache = getCache(context);
    if (cache != null) {
      Timber.d("Setting cache");
      client.setCache(cache);
    }
  }

  /**
   * Call this to reset the cache-size
   *
   * @param context
   */
  public static void updateCacheSize(Context context) {
    try {
      HttpLoaderImpl fl = getInstance(context);
      Cache cache = fl.client.getCache();
      if (cache != null) {
        cache.flush();
        cache.close();
        cache.delete();
      }
      fl.client.setCache(getCache(context));
    }
    catch (IOException ex) {
      Timber.w(ex, "Could not update Cachesize");
    }
  }

  /**
   * Builds a cache as configured in preferences
   *
   * @param ctx
   * @return
   */
  private static Cache getCache(Context ctx) {
    int cacheSize = PreferenceHelper.getCacheSize(ctx);
    Timber.d("Setting cachesize to %d MB", cacheSize);
    if (cacheSize > 0) {
      File cacheDir = ctx.getDir("http_cache", Context.MODE_PRIVATE);
      Timber.d("Using cachedir %s", cacheDir.getPath());
      return new Cache(cacheDir, cacheSize * 1024 * 1024);
    }
    Timber.d("No cache configured");
    return null;
  }

  /**
   *
   * @param ctx
   */
  public void loadFeedSummary(Context ctx) {
    loadFeedSummary(new FeedLoaderCallbackImpl(ctx));
  }

  /**
   *
   * @param callback
   */
  public void loadFeedSummary(FeedLoaderCallback callback) {
    Request request = new Request.Builder()
            .cacheControl(new CacheControl.Builder()
                    .maxAge(0, TimeUnit.SECONDS)
                    .build())
            .url(BuildConfig.FEED_URL).build();
    client.newCall(request).enqueue(callback);
  }

  @Override
  public Response loadUrl(String url) throws IOException {
    Request request = new Request.Builder().url(url).build();
    return client.newCall(request).execute();
  }

  @Override
  public void precacheUrl(String url) {
    Request request = new Request.Builder().url(url).build();
    client.newCall(request).enqueue(new PrecacheCallback());
  }

  @Override
  public Picasso getPicasso() {
    if (picasso == null) {
      picasso = new Picasso.Builder(context).downloader(new OkHttpDownloader(client)).build();
      picasso.setLoggingEnabled(BuildConfig.DEBUG);
      picasso.setIndicatorsEnabled(BuildConfig.DEBUG);
    }
    return picasso;
  }

}
