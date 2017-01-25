package com.acmebutchers.app.presentation.base;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.acmebutchers.app.AndroidApplication;
import com.acmebutchers.app.common.di.components.ApplicationComponent;
import com.acmebutchers.app.common.di.modules.ActivityModule;
import com.acmebutchers.app.presentation.navigation.Navigator;

import javax.inject.Inject;

/**
 * Base {@link android.support.v7.app.AppCompatActivity} class for every Activity in this application.
 */
public abstract class BaseActivity extends AppCompatActivity {

  @Inject
  protected Navigator navigator;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    this.getApplicationComponent().inject(this);
  }

  /**
   * Adds a {@link Fragment} to this activity's layout.
   *
   * @param containerViewId The container view to where add the fragment.
   * @param fragment        The fragment to be added.
   */
  protected void addFragment(int containerViewId, Fragment fragment) {
    FragmentTransaction fragmentTransaction = this.getFragmentManager().beginTransaction();
    fragmentTransaction.add(containerViewId, fragment);
    fragmentTransaction.commit();
  }

  /**
   * Replace a {@link Fragment} to this activity's layout.
   *
   * @param containerViewId The container view to where replace the fragment.
   * @param fragment        The fragment to be added.
   */
  protected void replaceFragment(int containerViewId, Fragment fragment) {
    FragmentTransaction fragmentTransaction = this.getFragmentManager().beginTransaction();
    fragmentTransaction.replace(containerViewId, fragment);
    fragmentTransaction.commit();
  }

  /**
   * Get the Main Application component for dependency injection.
   *
   * @return {@link com.acmebutchers.app.common.di.components.ApplicationComponent}
   */
  protected ApplicationComponent getApplicationComponent() {
    return ((AndroidApplication) getApplication()).getComponent();
  }

  /**
   * Get an Activity module for dependency injection.
   *
   * @return {@link com.acmebutchers.app.common.di.modules.ActivityModule}
   */
  protected ActivityModule getActivityModule() {
    return new ActivityModule(this);
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    boolean result = super.onOptionsItemSelected(item);

    switch (item.getItemId()) {
      case android.R.id.home:
        FragmentManager fm = getFragmentManager();
        if (fm.getBackStackEntryCount() > 0) {
          fm.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        } else {
          finish();
        }
        result = true;
        break;
      default:
        break;
    }

    return result;
  }

  @Override
  public void onBackPressed() {
    FragmentManager fm = getFragmentManager();

    if (fm.getBackStackEntryCount() > 0) {
      fm.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
    } else {
      super.onBackPressed();
    }
  }
}
