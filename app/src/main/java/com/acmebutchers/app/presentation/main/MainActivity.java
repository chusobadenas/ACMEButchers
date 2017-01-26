package com.acmebutchers.app.presentation.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.acmebutchers.app.R;
import com.acmebutchers.app.common.di.HasComponent;
import com.acmebutchers.app.common.di.components.DaggerMainComponent;
import com.acmebutchers.app.common.di.components.MainComponent;
import com.acmebutchers.app.presentation.base.BaseActivity;
import com.acmebutchers.app.presentation.map.MapFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Main activity
 */
public class MainActivity extends BaseActivity implements HasComponent<MainComponent> {

  @BindView(R.id.drawer_layout)
  DrawerLayout drawerLayout;
  @BindView(R.id.navigation_view)
  NavigationView navigationView;
  @BindView(R.id.toolbar)
  Toolbar toolbar;

  private MainComponent mainComponent;

  public static Intent getCallingIntent(Context context) {
    return new Intent(context, MainActivity.class);
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    ButterKnife.bind(this);
    initializeInjector();
    setupView();
    if (savedInstanceState == null) {
      addFragment(R.id.fragmentContainer, MainFragment.newInstance());
    }
  }

  private void initializeInjector() {
    mainComponent = DaggerMainComponent.builder()
        .applicationComponent(getApplicationComponent())
        .activityModule(getActivityModule())
        .build();
  }

  private void setupView() {
    // Load toolbar
    setSupportActionBar(toolbar);
    // Load drawer menu options
    navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
      @Override
      public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Checking if the item is in checked state or not, if not make it in checked state
        item.setChecked(!item.isChecked());

        // Closing drawer on item click
        drawerLayout.closeDrawers();

        // Get current fragment
        Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.fragmentContainer);

        switch (item.getItemId()) {
          case R.id.home:
            // Navigate to home
            if (!(currentFragment instanceof MainFragment)) {
              replaceFragment(R.id.fragmentContainer, MainFragment.newInstance());
            }
            break;
          case R.id.map:
            // Navigate to map
            if (!(currentFragment instanceof MapFragment)) {
              replaceFragment(R.id.fragmentContainer, MapFragment.newInstance());
            }
            break;
          case R.id.tweets:
            // TODO: Navigate to tweets
            break;
          default:
            break;
        }

        return true;
      }
    });
    // Drawer toggle
    ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
        R.string.open_drawer, R.string.close_drawer);
    toggle.setDrawerIndicatorEnabled(true);
    drawerLayout.addDrawerListener(toggle);
    toggle.syncState();
  }

  @Override
  public MainComponent getComponent() {
    return mainComponent;
  }
}
