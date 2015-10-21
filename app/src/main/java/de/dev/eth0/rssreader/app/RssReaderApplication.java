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
package de.dev.eth0.rssreader.app;

import android.app.AlarmManager;
import android.app.Application;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.Log;
import com.crashlytics.android.Crashlytics;
import de.dev.eth0.rssreader.BuildConfig;
import de.dev.eth0.rssreader.app.service.FeedReaderIntentService;
import de.dev.eth0.rssreader.app.util.PreferenceHelper;
import io.fabric.sdk.android.Fabric;
import timber.log.Timber;
import timber.log.Timber.DebugTree;

/**
 * the Application class
 */
public class RssReaderApplication extends Application {

  private PackageInfo packageInfo;

  @Override
  public void onCreate() {
    super.onCreate();
    // Setup crash-reporting
    Fabric.with(this, new Crashlytics());
    // Setup logging
    if (BuildConfig.DEBUG) {
      Timber.plant(new DebugTree());
    }
    else {
      Timber.plant(new CrashReportingTree());
    }
    setUpdateAlarm();
  }

  /**
   * Sets the update alarm. Cancels all running update intents first.
   */
  public void setUpdateAlarm() {
    int updateFrequency = PreferenceHelper.getUpdateFrequency(this);
    Timber.d("Setting update alarm to %d minutes", updateFrequency);
    AlarmManager am = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
    Intent serviceIntent = new Intent(this, FeedReaderIntentService.class);
    PendingIntent pendingIntent = PendingIntent.getService(this, 0, serviceIntent, PendingIntent.FLAG_UPDATE_CURRENT);
    am.cancel(pendingIntent);
    if (updateFrequency > 0) {
      am.setInexactRepeating(
              AlarmManager.RTC_WAKEUP,
              System.currentTimeMillis(),
              updateFrequency * 1000 * 60,
              pendingIntent);
    }
  }

  /**
   * @return the application version name
   */
  public String applicationVersionName() {
    return getPackageInfo().versionName;
  }

  /**
   *
   * @return application version code
   */
  public int applicationVersionCode() {
    return getPackageInfo().versionCode;
  }

  /**
   *
   * @return
   */
  public PackageInfo getPackageInfo() {
    if (packageInfo == null) {
      try {
        packageInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
      }
      catch (final PackageManager.NameNotFoundException x) {
        throw new RuntimeException(x);
      }
    }
    return packageInfo;
  }

  /**
   * A tree which logs important information for crash reporting.
   */
  private static class CrashReportingTree extends Timber.Tree {

    @Override
    protected void log(int priority, String tag, String message, Throwable t) {
      if (priority == Log.VERBOSE || priority == Log.DEBUG) {
        return;
      }

      Crashlytics.getInstance().core.log(priority, tag, message);

      if (t != null && priority >= Log.WARN) {
        Crashlytics.getInstance().core.logException(t);
      }
    }
  }
}
