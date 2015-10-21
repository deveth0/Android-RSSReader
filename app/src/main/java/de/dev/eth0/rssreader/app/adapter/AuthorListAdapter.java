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

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import de.dev.eth0.rssreader.R;
import de.dev.eth0.rssreader.app.ui.AbstractBaseActivity;
import de.dev.eth0.rssreader.core.data.model.Author;
import java.util.List;

/**
 *
 * @author deveth0
 */
public class AuthorListAdapter extends AbstractRecyclerViewAdapter<AuthorListHolder, Author> {

  /**
   *
   * @param content
   * @param activity
   * @param listener
   */
  public AuthorListAdapter(List<Author> content, AbstractBaseActivity activity, View.OnClickListener listener) {
    super(content, activity, listener);
  }

  @Override
  public AuthorListHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
    View itemView = LayoutInflater.
            from(viewGroup.getContext()).
            inflate(R.layout.author_list_entry, viewGroup, false);
    itemView.setOnClickListener(getListener());
    return new AuthorListHolder(itemView, getActivity());
  }

}
