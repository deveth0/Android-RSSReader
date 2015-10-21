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
package de.dev.eth0.rssreader.app.ui;

import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import butterknife.Bind;
import de.dev.eth0.rssreader.BuildConfig;
import de.dev.eth0.rssreader.R;
import de.dev.eth0.rssreader.core.Constants;

public abstract class AbstractNavigationDrawerActivity extends AbstractBaseActivity implements NavigationView.OnNavigationItemSelectedListener {

  @Bind(R.id.drawer_layout)
  @Nullable
  protected DrawerLayout drawerLayout;

  @Bind(R.id.navigation_drawer_headerimage)
  @Nullable
  protected ImageView headerimage;

  @Bind(R.id.navigation_drawer)
  @Nullable
  protected NavigationView navView;
  @Bind(R.id.navigation_drawer_bottom)
  @Nullable
  protected NavigationView navViewBottom;
  private final Handler drawerActionHandler = new Handler();
  private static final long DRAWER_CLOSE_DELAY_MS = 350;

  private ActionBarDrawerToggle actionBarDrawerToggle;

  public abstract int getMenuItemId();

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    drawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
    // configure toogle
    actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.app_name, R.string.app_name);
    drawerLayout.setDrawerListener(actionBarDrawerToggle);

    navView.setNavigationItemSelectedListener(this);
    navViewBottom.setNavigationItemSelectedListener(this);
    navView.getMenu().findItem(getMenuItemId()).setChecked(true);

    headerimage.setOnClickListener(new View.OnClickListener() {

      @Override
      public void onClick(View v) {
        startActivity(new Intent(Intent.ACTION_VIEW).setData(Uri.parse(BuildConfig.PAGE_URL)));
      }
    });
  }

  /**
   * Handles clicks on the navigation menu.
   *
   * @param menuItem
   * @return
   */
  @Override
  public boolean onNavigationItemSelected(final MenuItem menuItem) {
    // update highlighted item in the navigation menu
    final int itemId = menuItem.getItemId();

    // allow some time after closing the drawer before performing real navigation
    // so the user can see what is happening
    drawerLayout.closeDrawers();
    drawerActionHandler.postDelayed(new Runnable() {
      @Override
      public void run() {
        //Don't do anything if the item has already been selected
        if (getMenuItemId() != itemId) {
          switch (itemId) {
            case R.id.nav_summaries:
              startActivity(new Intent(AbstractNavigationDrawerActivity.this, MainActivity.class));
              break;
            case R.id.nav_bookmarked:
              startActivity(new Intent(AbstractNavigationDrawerActivity.this, BookmarkedActivity.class));
              break;
            case R.id.nav_authors:
              startActivity(new Intent(AbstractNavigationDrawerActivity.this, AuthorsActivity.class));
              break;
            case R.id.nav_settings:
              startActivity(new Intent(AbstractNavigationDrawerActivity.this, SettingsActivity.class));
              break;
            case R.id.nav_feedback:
              Intent email = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto", Constants.REPORT_EMAIL, null));
              email.putExtra(Intent.EXTRA_SUBJECT, Constants.FEEDBACK_SUBJECT);
              startActivity(Intent.createChooser(email, null));
              break;
            case R.id.nav_about:
              startActivity(new Intent(Intent.ACTION_VIEW).setData(Uri.parse(BuildConfig.ABOUT_URL)));
              break;
            case R.id.nav_donate:
              //TODO: add bitcoin option
              startActivity(new Intent(Intent.ACTION_VIEW).setData(Uri.parse(BuildConfig.DONATE_URL)));
              break;
            default:
              // ignore
              break;
          }
        }
      }
    }, DRAWER_CLOSE_DELAY_MS);
    return true;
  }

  /**
   * When using the ActionBarDrawerToggle, you must call it during onPostCreate() and onConfigurationChanged()...
   */
  @Override
  protected void onPostCreate(Bundle savedInstanceState) {
    super.onPostCreate(savedInstanceState);
    if (actionBarDrawerToggle != null) {
      actionBarDrawerToggle.syncState();
    }
  }

  @Override
  public void onConfigurationChanged(Configuration newConfig) {
    super.onConfigurationChanged(newConfig);
    if (actionBarDrawerToggle != null) {
      actionBarDrawerToggle.onConfigurationChanged(newConfig);
    }
  }

}
