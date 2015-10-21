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
package de.dev.eth0.rssreader.app.data;

import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;
import de.dev.eth0.rssreader.app.ui.fragments.UpdateableFragment;

/**
 * Content observer which calls the onUpdate Method
 *
 * @author deveth0
 */
public class UpdateViewContentObserver extends ContentObserver {
  private final UpdateableFragment fragment;

  public UpdateViewContentObserver(Handler handler, UpdateableFragment fragment) {
    super(handler);
    this.fragment = fragment;
  }

  @Override
  public void onChange(boolean selfChange, Uri uri) {
    fragment.onUpdate();
  }

  @Override
  public void onChange(boolean selfChange) {
    onChange(selfChange, null);
  }
}
