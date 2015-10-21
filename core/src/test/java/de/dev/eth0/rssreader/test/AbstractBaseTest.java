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
package de.dev.eth0.rssreader.test;

import de.dev.eth0.rssreader.core.data.helper.DataHelper;
import de.dev.eth0.rssreader.core.data.model.FeedEntry;
import de.dev.eth0.rssreader.core.data.model.impl.FeedEntryImpl;
import junit.framework.TestCase;
import org.junit.Before;
import timber.log.Timber;

public abstract class AbstractBaseTest extends TestCase {

  private final TestDataHelper datahelper = new TestDataHelper();

  protected final static FeedEntry FEED_ENTRY_TEST_1 = new FeedEntryImpl(
          1441978578000L,
          "Test title",
          "Some Description text",
          "The content is usually longer <a href=\"http://foobar.de/test_2.html\">and can contain links",
          "The author",
          "http://foobar.de/test_1.html",
          "http://foobar.de/test_1.html#comments",
          "Category 1,Category 2");

  @Before
  @Override
  public void setUp() throws Exception {
    Timber.plant(new Timber.Tree() {

      @Override
      protected void log(int i, String tag, String msg, Throwable thrwbl) {
        System.out.println(msg);
        if (thrwbl != null) {
          thrwbl.printStackTrace();
        }
      }

    });
  }

  public DataHelper getDataHelper() {
    return datahelper;
  }

  public void insertData() {

  }
}
