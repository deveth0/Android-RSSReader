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
package de.dev.eth0.rssreader.app.data.helper;

import android.content.Context;
import de.dev.eth0.rssreader.app.data.provider.DataProviderHelperImpl;
import de.dev.eth0.rssreader.core.data.helper.DataHelper;
import de.dev.eth0.rssreader.core.data.provider.DataProviderHelper;

/**
 *
 * @author amuthmann
 */
public class DataHelperImpl implements DataHelper {
  private final Context ctx;
  private DataProviderHelper dataProviderHelper;

  public DataHelperImpl(Context ctx) {
    this.ctx = ctx;
  }

  @Override
  public DataProviderHelper getDataProviderHelper() {
    if (dataProviderHelper == null) {
      dataProviderHelper = new DataProviderHelperImpl(ctx);
    }
    return dataProviderHelper;
  }
}
