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
import de.dev.eth0.rssreader.test.AbstractBaseTest;
import java.io.InputStream;
import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertTrue;

/**
 *
 * @author amuthmann
 */
public class RssFeedParserTest extends AbstractBaseTest {

  public void testInvalidXmlParsing() {
    InputStream stream = RssFeedParserTest.class.getResourceAsStream("/feed_invalid.xml");
    assertNotNull(stream);
    List<FeedEntry> entries = RssFeedParser.parseFeed(stream);
    assertTrue(entries.isEmpty());
  }

  public void testValidXmlParsing() {
    InputStream stream = RssFeedParserTest.class.getResourceAsStream("/feed_valid.xml");
    assertNotNull(stream);
    List<FeedEntry> entries = RssFeedParser.parseFeed(stream);
    assertEquals(2, entries.size());
    FeedEntry entry = entries.get(0);
    assertEquals(FEED_ENTRY_TEST_1.getMetadata().getCategories(), entry.getMetadata().getCategories());
    assertEquals(FEED_ENTRY_TEST_1.getMetadata().getCommentsLink(), entry.getMetadata().getCommentsLink());
    assertEquals(FEED_ENTRY_TEST_1.getMetadata().getCreator(), entry.getMetadata().getCreator());
    assertEquals(FEED_ENTRY_TEST_1.getMetadata().getDescription(), entry.getMetadata().getDescription());
    assertEquals(FEED_ENTRY_TEST_1.getMetadata().getId(), entry.getMetadata().getId());
    assertEquals(FEED_ENTRY_TEST_1.getMetadata().getLink(), entry.getMetadata().getLink());
    assertEquals(FEED_ENTRY_TEST_1.getMetadata().getTitle(), entry.getMetadata().getTitle());
    assertEquals(FEED_ENTRY_TEST_1.getContent(), entry.getContent());
  }

  public void testPartlyValidXmlParsing() {
    InputStream stream = RssFeedParserTest.class.getResourceAsStream("/feed_partly_valid.xml");
    assertNotNull(stream);
    List<FeedEntry> entries = RssFeedParser.parseFeed(stream);
    assertEquals(1, entries.size());
    FeedEntry entry = entries.get(0);
    assertEquals(FEED_ENTRY_TEST_1.getMetadata().getCategories(), entry.getMetadata().getCategories());
    assertEquals(FEED_ENTRY_TEST_1.getMetadata().getCommentsLink(), entry.getMetadata().getCommentsLink());
    assertEquals(FEED_ENTRY_TEST_1.getMetadata().getCreator(), entry.getMetadata().getCreator());
    assertEquals(FEED_ENTRY_TEST_1.getMetadata().getDescription(), entry.getMetadata().getDescription());
    assertEquals(FEED_ENTRY_TEST_1.getMetadata().getId(), entry.getMetadata().getId());
    assertEquals(FEED_ENTRY_TEST_1.getMetadata().getLink(), entry.getMetadata().getLink());
    assertEquals(FEED_ENTRY_TEST_1.getMetadata().getTitle(), entry.getMetadata().getTitle());
    assertEquals(FEED_ENTRY_TEST_1.getContent(), entry.getContent());

  }
}
