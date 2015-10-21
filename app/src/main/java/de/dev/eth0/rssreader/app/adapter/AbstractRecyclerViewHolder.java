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
package de.dev.eth0.rssreader.app.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import butterknife.ButterKnife;
import de.dev.eth0.rssreader.app.ui.AbstractBaseActivity;

/**
 * Abstract holder for recycler view
 *
 * @author deveth0
 * @param <U>
 */
public abstract class AbstractRecyclerViewHolder<U> extends RecyclerView.ViewHolder {

  private final AbstractBaseActivity activity;
  private U entry;

  /**
   *
   * @param itemView
   * @param activity
   */
  public AbstractRecyclerViewHolder(View itemView, AbstractBaseActivity activity) {
    super(itemView);
    this.activity = activity;
    ButterKnife.bind(this, itemView);
  }

  /**
   *
   * @return
   */
  protected AbstractBaseActivity getActivity() {
    return activity;
  }

  public void setEntry(U entry) {
    this.entry = entry;
    updateContent();
  }

  public U getEntry() {
    return entry;
  }

  protected abstract void updateContent();

}
