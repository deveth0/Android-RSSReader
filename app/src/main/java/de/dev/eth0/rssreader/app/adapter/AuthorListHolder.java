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

import android.text.Html;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
import android.view.View;
import android.widget.TextView;
import butterknife.Bind;
import de.dev.eth0.rssreader.R;
import de.dev.eth0.rssreader.app.ui.AbstractBaseActivity;
import de.dev.eth0.rssreader.core.data.model.Author;

/**
 * @author amuthmann
 */
public class AuthorListHolder extends AbstractRecyclerViewHolder<Author> {

  @Bind(R.id.author_name)
  protected TextView authorName;

  @Bind(R.id.author_description)
  protected TextView authorDescription;

  @Bind(R.id.author_twitter)
  protected TextView authorTwitter;
  @Bind(R.id.author_email)
  protected TextView authorEmail;
  @Bind(R.id.author_phone)
  protected TextView authorPhone;

  /**
   *
   * @param itemView
   * @param activity
   */
  public AuthorListHolder(View itemView, AbstractBaseActivity activity) {
    super(itemView, activity);

  }

  @Override
  protected void updateContent() {
    if (getEntry() != null) {
      authorName.setText(getEntry().getName());
      authorDescription.setText(getEntry().getDescription());
      if (!TextUtils.isEmpty(getEntry().getTwitter())) {
        authorTwitter.setText(Html.fromHtml(
                "<a href=\"https://twitter.com/" + getEntry().getTwitter().replace("@", "") + "\">" + getEntry().getTwitter() + "</a>"));
        authorTwitter.setMovementMethod(LinkMovementMethod.getInstance());
      }
      authorPhone.setText(getEntry().getPhone());
      Linkify.addLinks(authorPhone, Linkify.PHONE_NUMBERS);
      authorEmail.setText(getEntry().getEmail());
      Linkify.addLinks(authorEmail, Linkify.EMAIL_ADDRESSES);

    }
  }
}
