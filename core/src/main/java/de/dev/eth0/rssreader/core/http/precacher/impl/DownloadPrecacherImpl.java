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
package de.dev.eth0.rssreader.core.http.precacher.impl;

import de.dev.eth0.rssreader.core.Constants;
import de.dev.eth0.rssreader.core.http.HttpLoader;
import de.dev.eth0.rssreader.core.http.precacher.AbstractUrlPrecacher;
import de.dev.eth0.rssreader.core.http.precacher.PrecacheHelper;

public class DownloadPrecacherImpl extends AbstractUrlPrecacher implements PrecacheHelper.UrlPrecacher {

  public DownloadPrecacherImpl(HttpLoader loader) {
    super(loader);
  }

  @Override
  public boolean accepts(String url) {
    return Constants.PATTERN_DOWNLOAD.matcher(url).matches();
  }
}
