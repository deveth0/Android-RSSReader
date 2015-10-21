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
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.dev.eth0.rssreader.core;

import java.util.regex.Matcher;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;

/**
 *
 * @author amuthmann
 */
public class ConstantsTest {

  private final static String youtubeId = "NLqAF9hrVbY";

  private final String[] youtubeUrls = new String[]{
    "http://youtu.be/NLqAF9hrVbY",
    "http://www.youtube.com/embed/NLqAF9hrVbY",
    "https://www.youtube.com/embed/NLqAF9hrVbY",
    "http://www.youtube.com/v/NLqAF9hrVbY?fs=1&hl=en_US",
    "http://www.youtube.com/watch?v=NLqAF9hrVbY",
    "http://gdata.youtube.com/feeds/api/videos/NLqAF9hrVbY",
    "http://www.youtube.com/watch?v=NLqAF9hrVbY&feature=g-vrec",
    "//www.youtube.com/watch?v=NLqAF9hrVbY&feature=g-vrec",
    "https://www.youtube.com/embed/NLqAF9hrVbY?rel=0"};

  private final String[] imageUrls = new String[]{
    "http://foo.bar/ipsum.png",
    "http://foo.bar/ipsum.jpg",
    "http://foo.bar/ipsum.gif",
    "http://foo.bar/ipsum.bmp",
    "http://foo.bar/ipsum.png?param",};

  private final String[] invalidImageUrls = new String[]{
    "http://foo.bar/noimage.pdf"};

  private final String testIFrame1
          = "<iframe width=\"730\" height=\"411\" src=\"https://www.youtube.com/embed/NLqAF9hrVbY?rel=0\" frameborder=\"0\" allowfullscreen></iframe>";

  private final String[] iframeContent = new String[]{
    "<p><div style=\"width: 370px\" class=\"wp-caption alignright\"><a href=\"https://cdn.foobar.ipsum/wp-upload/8178544401_041275b2b2_z.jpg\">"
    + "<img src=\"https://cdn.foobar.ipsum/wp-upload/8178544401_041275b2b2_z.jpg\" alt=\"mitch altman\" width=\"351\" /></a>"
    + testIFrame1
    + "</p>"
  };

  @Test

  public void testYoutubePattern() {
    for (String url : youtubeUrls) {
      Matcher matcher = Constants.PATTERN_YOUTUBE.matcher(url);
      assertTrue(url + " matches", matcher.matches());
      assertEquals("id does not match", youtubeId, matcher.group(1));
    }
  }

  @Test
  public void testImageUrls() {
    for (String url : imageUrls) {
      Matcher matcher = Constants.PATTERN_IMAGES.matcher(url);
      assertTrue(url + " matches", matcher.matches());
    }
    for (String url : invalidImageUrls) {
      Matcher matcher = Constants.PATTERN_IMAGES.matcher(url);
      assertFalse(url + " matches", matcher.matches());
    }
  }
  @Test
  public void testIframeContent() {
    for (String url : iframeContent) {
      Matcher matcher = Constants.PATTERN_IFRAMES.matcher(url);
      assertTrue(matcher.find());
      assertEquals(testIFrame1, matcher.group(0));
      assertEquals("https://www.youtube.com/embed/NLqAF9hrVbY?rel=0", matcher.group(2));
    }

  }
}
