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
package de.dev.eth0.rssreader.core.data.parser;

import de.dev.eth0.rssreader.core.data.model.FeedEntry;
import de.dev.eth0.rssreader.core.data.model.impl.FeedEntryImpl;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;
import timber.log.Timber;

/**
 * Parser for RSS Feed
 *
 * @author amuthmann
 */
public class RssFeedParser {

  private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz", Locale.ENGLISH);

  public static List<FeedEntry> parseFeed(InputStream feed) {
    List<FeedEntry> ret = new ArrayList<>();
    try {
      XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
      factory.setNamespaceAware(false);
      XmlPullParser xpp = factory.newPullParser();
      xpp.setInput(feed, null);

      // Returns the type of current event: START_TAG, END_TAG, etc..
      int eventType = xpp.getEventType();
      FeedEntry feedEntry = null;
      while (eventType != XmlPullParser.END_DOCUMENT) {
        if (eventType == XmlPullParser.START_TAG) {
          if (xpp.getName().equalsIgnoreCase("item")) {
            feedEntry = new FeedEntryImpl(null);
          }
          else if (xpp.getName().equalsIgnoreCase("title")) {
            if (feedEntry != null) {
              feedEntry.getMetadata().setTitle(xpp.nextText());
            }
          }
          else if (xpp.getName().equalsIgnoreCase("dc:creator")) {
            if (feedEntry != null) {
              feedEntry.getMetadata().setCreator(xpp.nextText());
            }
          }
          else if (xpp.getName().equalsIgnoreCase("pubDate")) {
            if (feedEntry != null) {
              String date = xpp.nextText();
              try {
                feedEntry.getMetadata().setId(DATE_FORMAT.parse(date).getTime());
              }
              catch (ParseException pe) {
                Timber.w("Could nor parse date %s", date);
              }
            }
          }
          else if (xpp.getName().equalsIgnoreCase("content:encoded")) {
            if (feedEntry != null) {
              feedEntry.setContent(xpp.nextText());
            }
          }
          else if (xpp.getName().equalsIgnoreCase("link")) {
            if (feedEntry != null) {
              feedEntry.getMetadata().setLink(xpp.nextText());
            }
          }
          else if (xpp.getName().equalsIgnoreCase("comments")) {
            if (feedEntry != null) {
              feedEntry.getMetadata().setCommentsLink(xpp.nextText());
            }
          }
          else if (xpp.getName().equalsIgnoreCase("category")) {
            if (feedEntry != null) {
              feedEntry.getMetadata().setCategories(feedEntry.getMetadata().getCategories() == null
                      ? xpp.nextText()
                      : feedEntry.getMetadata().getCategories() + "," + xpp.nextText());
            }
          }
          else if (xpp.getName().equalsIgnoreCase("description")) {
            if (feedEntry != null) {
              feedEntry.getMetadata().setDescription(xpp.nextText());
            }
          }
        }
        else if (eventType == XmlPullParser.END_TAG && xpp.getName().equalsIgnoreCase("item")) {
          if (feedEntry != null) {
            Timber.d("Parsed FeedEntry %s", feedEntry.toString());
            if (feedEntry.isValid()) {
            ret.add(feedEntry);
            }
            feedEntry = null;
          }
        }
        eventType = xpp.next(); // move to next element
      }
    }
    catch (XmlPullParserException | IOException xppe) {
      Timber.w(xppe, "Could not parse xml");
    }
    return ret;
  }
}
