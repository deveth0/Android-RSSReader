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

import de.dev.eth0.rssreader.core.data.model.Author;
import de.dev.eth0.rssreader.test.AbstractBaseTest;
import java.io.InputStream;
import java.util.List;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertTrue;


/**
 *
 * @author amuthmann
 */
public class AuthorJsonParserTest extends AbstractBaseTest {

  /**
   * Test of parseAuthors method, of class AuthorJsonParser.
   */
  @Test
  public void testParseAuthors() {
    InputStream stream = AuthorJsonParser.class.getResourceAsStream("/authors.json");
    assertNotNull(stream);
    List<Author> authors = AuthorJsonParser.parseAuthors(stream);
    assertTrue(!authors.isEmpty());
    assertEquals(2, authors.size());
    Author author1 = authors.get(0);
    assertEquals("Author 1", author1.getName());
    assertEquals("ipsum lorem <br/> foobar", author1.getDescription());
    assertEquals("foo@bar.com", author1.getEmail());
    assertEquals("@foobar", author1.getTwitter());
    assertEquals("+0123456789", author1.getPhone());
  }

}
