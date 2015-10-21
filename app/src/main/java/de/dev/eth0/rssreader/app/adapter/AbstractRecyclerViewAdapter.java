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
import de.dev.eth0.rssreader.app.ui.AbstractBaseActivity;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author amuthmann
 * @param <T>
 * @param <U>
 */
public abstract class AbstractRecyclerViewAdapter<T extends AbstractRecyclerViewHolder<U>, U> extends RecyclerView.Adapter<T> {

  private List<U> content;
  private final View.OnClickListener listener;
  private final AbstractBaseActivity activity;

  public AbstractRecyclerViewAdapter(List<U> content, AbstractBaseActivity activity, View.OnClickListener listener) {
    this.listener = listener;
    this.activity = activity;
    if (content != null) {
      this.content = content;
    }
    else {
      this.content = new ArrayList<>();
    }
  }

  public void updateContent(List<U> content) {
    this.content = content;
    notifyDataSetChanged();
  }

  public View.OnClickListener getListener() {
    return listener;
  }


  public AbstractBaseActivity getActivity() {
    return activity;
  }

  @Override
  public void onBindViewHolder(T viewHolder, int i) {
    U entry = content.get(i);
    viewHolder.setEntry(entry);
  }

  @Override
  public int getItemCount() {
    return content.size();
  }

}
