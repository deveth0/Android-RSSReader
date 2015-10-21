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
package de.dev.eth0.rssreader.app.util;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import com.squareup.okhttp.Request;
import de.dev.eth0.rssreader.R;
import de.dev.eth0.rssreader.app.data.database.tables.FeedEntryTable;
import de.dev.eth0.rssreader.app.ui.MainActivity;
import de.dev.eth0.rssreader.core.data.model.FeedEntry;
import de.dev.eth0.rssreader.core.util.NotificationManager;
import java.io.IOException;
import java.util.List;
import timber.log.Timber;

public class NotificationManagerImpl implements NotificationManager {

  private static final int ID_NEW_FEEDS = 0;
  private static final int ID_UPDATE_FAILED = 1;

  private final Context context;

  public NotificationManagerImpl(Context context) {
    this.context = context;
  }

  @Override
  public void sendFeedUpdatedNotification(List<FeedEntry> newFeeds) {
    Timber.d("sendFeedUpdatedNotifications %d", newFeeds.size());
    if (isNotificationDisabled() || newFeeds.isEmpty()) {
      return;
    }
    android.app.NotificationManager notificationManager
            = (android.app.NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);

    NotificationCompat.Builder builder
            = new NotificationCompat.Builder(context)
            .setSmallIcon(R.drawable.icon_empty_18dp)
            .setContentTitle(context.getString(R.string.notification_title))
            .setContentText(context.getString(R.string.notification_text, newFeeds.size()));

    if (newFeeds.size() > 1) {
      NotificationCompat.InboxStyle extended
              = new NotificationCompat.InboxStyle();
      extended.setBigContentTitle(context.getString(R.string.notification_title_extended));
      for (FeedEntry summary : newFeeds) {
        extended.addLine(summary.getMetadata().getTitle());
      }
      builder.setStyle(extended);
    }

    Intent resultIntent = new Intent(context, MainActivity.class);
    if (newFeeds.size() == 1) {
      builder.setContentText(newFeeds.get(0).getMetadata().getTitle());
      resultIntent.putExtra(FeedEntryTable.COLUMN_ID, newFeeds.get(0).getMetadata().getId());
    }
    TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
    stackBuilder.addNextIntentWithParentStack(resultIntent);
    PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
    builder.setContentIntent(resultPendingIntent);

    builder.setAutoCancel(true);
    Notification notification = builder.build();
    notification.flags = Notification.FLAG_AUTO_CANCEL;

    notificationManager.notify(ID_NEW_FEEDS, notification);
  }

  @Override
  public void sendFeedUpdateFailedNotification(Request request, IOException ioe) {
    //TODO: add more information into the notification
    //TODO: don't display this notification multiple times, that's annoyin
    if (isNotificationDisabled()) {
      return;
    }
    android.app.NotificationManager notificationManager
            = (android.app.NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);

    NotificationCompat.Builder builder
            = new NotificationCompat.Builder(context)
            .setSmallIcon(R.drawable.icon_empty_18dp)
            .setContentTitle(context.getString(R.string.notification_update_failed_title))
            .setContentText(ioe.getMessage())
            .setAutoCancel(true);

    Notification notification = builder.build();
    notification.flags = Notification.FLAG_AUTO_CANCEL;
    notificationManager.notify(ID_UPDATE_FAILED, notification);

  }


  private boolean isNotificationDisabled() {
    return PreferenceHelper.isNotificationsDisabled(context);
  }
}
