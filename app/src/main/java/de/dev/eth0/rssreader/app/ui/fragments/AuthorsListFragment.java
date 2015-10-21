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
package de.dev.eth0.rssreader.app.ui.fragments;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.Bind;
import butterknife.ButterKnife;
import de.dev.eth0.rssreader.R;
import de.dev.eth0.rssreader.app.adapter.AuthorListAdapter;
import de.dev.eth0.rssreader.app.ui.view.DividerItemDecoration;

/**
 *
 * @author deveth0
 */
public class AuthorsListFragment extends AbstractBaseFragment {

  private AuthorListAdapter adapter;

  @Bind(R.id.feed_summary_list)
  protected RecyclerView recList;

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.feed_summary_list_fragment, container, false);
    ButterKnife.bind(this, view);
    RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST);
    recList.addItemDecoration(itemDecoration);
    return view;
  }

  @Override
  public void onDestroyView() {
    super.onDestroyView();
    ButterKnife.unbind(this);
  }

  @Override
  public void onViewCreated(View view, Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    recList.setHasFixedSize(true);
    LinearLayoutManager llm = new LinearLayoutManager(getActivity());
    llm.setOrientation(LinearLayoutManager.VERTICAL);
    recList.setLayoutManager(llm);

    adapter = new AuthorListAdapter(getDataProviderHelper().getAuthors(), getBaseActivity(), null);
    recList.setAdapter(adapter);
  }

}
