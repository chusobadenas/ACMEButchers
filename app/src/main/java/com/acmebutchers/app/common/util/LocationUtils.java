package com.acmebutchers.app.common.util;

import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Build;
import android.provider.Settings;
import android.support.design.widget.Snackbar;
import android.text.TextUtils;
import android.view.View;

import com.acmebutchers.app.R;

import timber.log.Timber;

/**
 * Location utilities class
 */
public final class LocationUtils {

  /**
   * Empty constructor
   */
  private LocationUtils() {
  }

  /**
   * Checks if location is enabled or not
   *
   * @param context the context
   * @return true if location is enabled, false otherwise
   */
  public static boolean isLocationEnabled(Context context) {
    boolean enabled;
    int locationMode = Settings.Secure.LOCATION_MODE_OFF;

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
      try {
        locationMode = Settings.Secure.getInt(context.getContentResolver(), Settings.Secure.LOCATION_MODE);
      } catch (Settings.SettingNotFoundException exception) {
        Timber.e(exception);
      }

      enabled = locationMode != Settings.Secure.LOCATION_MODE_OFF;

    } else {
      String locationProviders = Settings.Secure.getString(context.getContentResolver(), LocationManager.PROVIDERS_CHANGED_ACTION);
      enabled = !TextUtils.isEmpty(locationProviders);
    }

    return enabled;
  }

  /**
   * Shows the location settings
   *
   * @param context the context
   * @param view    the root view
   */
  public static void showLocationSettings(final Context context, View view) {
    Snackbar.make(view, context.getString(R.string.enable_location), Snackbar.LENGTH_INDEFINITE)
        .setAction(android.R.string.ok, new View.OnClickListener() {
          @Override
          public void onClick(View view) {
            Intent viewIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            context.startActivity(viewIntent);
          }
        }).show();
  }
}
