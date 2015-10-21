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
package de.dev.eth0.rssreader.app.ui.dialog;

import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import butterknife.Bind;
import butterknife.ButterKnife;
import de.dev.eth0.rssreader.R;
import de.dev.eth0.rssreader.app.data.database.tables.FeedEntryTable;
import de.dev.eth0.rssreader.app.http.HttpLoaderImpl;
import timber.log.Timber;

/**
 *
 * @author amuthmann
 */
public class ImageOverlayDialog extends DialogFragment {

  @Bind(R.id.overlay_image)
  protected ImageView imageView;
  @Bind(R.id.overlay_close)
  protected ImageView closeButton;

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.image_overlay_dialog, container, false);
    ButterKnife.bind(this, view);
    return view;
  }

  @Override
  public void onDestroyView() {
    super.onDestroyView();
    ButterKnife.unbind(this);
  }

  @Override
  public void onViewCreated(View view, Bundle savedInstanceState) {
    if (getArguments() != null && getArguments().containsKey(FeedEntryTable.COLUMN_LINK)) {
      String url = getArguments().getString(FeedEntryTable.COLUMN_LINK, null);
      Timber.d("Display image %s in overlay", url);
      HttpLoaderImpl.getInstance(this.getActivity()).getPicasso().load(url).fit().centerInside().into(imageView);
      closeButton.setOnClickListener(new View.OnClickListener() {

        @Override
        public void onClick(View v) {
          dismiss();
        }
      });
    }
  }

  @Override
  public void onStart() {
    super.onStart();
    Dialog dialog = getDialog();
    if (dialog != null) {
      int width = ViewGroup.LayoutParams.MATCH_PARENT;
      int height = ViewGroup.LayoutParams.MATCH_PARENT;
      dialog.getWindow().setLayout(width, height);
      dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
    }
  }

  @Override
  public Dialog onCreateDialog(Bundle savedInstanceState) {
    Dialog dialog = super.onCreateDialog(savedInstanceState);
    dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
    return dialog;
  }

}
