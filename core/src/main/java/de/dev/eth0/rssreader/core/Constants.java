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
//CHECKSTYLE:OFF
package de.dev.eth0.rssreader.core;

import java.util.regex.Pattern;

/**
 * Constants only
 */
public final class Constants {

  /**
   * Email for reports
   */
  public static final String REPORT_EMAIL = "rssreader@dev-eth0.de";
  /**
   * Subject for Feedback
   */
  public static final String FEEDBACK_SUBJECT = "Feedback RSS Reader.";

  /**
   * Default update frequency
   */
  public static final int DEFAULT_UPDATE_FREQUENCY = 60;

  public static final int DEFAULT_CACHE_SIZE = 10;

  public static final String KEY_PREF_UPDATE_FREQUENCY = "updates_frequency";
  public static final String KEY_PREF_UPDATE_WIFI_ONLY = "updates_wifi_only";
  public static final String KEY_PREF_NOTIFICATION_ENABLED = "notifications_enabled";
  public static final String KEY_PREF_CACHE_SIZE = "cache_size";
  public static final String KEY_PREF_CACHE_IMAGES = "cache_images";
  public static final String KEY_PREF_CACHE_PDF = "cache_pdf";

  public static final Pattern PATTERN_IMAGES = Pattern.compile("([^\\s]+(\\.(?i)(jpg|png|gif|bmp))(\\?.*)?$)");
  public static final Pattern PATTERN_DOWNLOAD = Pattern.compile("([^\\s]+(\\.(?i)(pdf))$)");
  public static final Pattern PATTERN_YOUTUBE = Pattern.compile("^[https?://|//].*(?:youtu.be/|v/|u/\\\\w/|embed/|videos/|\\?v=)([^#&?]*).*$");
  public static final String YOUTUBE_PREVIEW_URL = "http://img.youtube.com/vi/%s/hqdefault.jpg";

  public static final Pattern PATTERN_IFRAMES = Pattern.compile("(<iframe.*?src=\"([^\"]+?)\"[^>]*?></iframe>)+");
}

//CHECKSTYLE:ON
