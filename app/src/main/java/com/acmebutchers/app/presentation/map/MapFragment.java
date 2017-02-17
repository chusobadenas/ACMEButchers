package com.acmebutchers.app.presentation.map;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.acmebutchers.app.R;
import com.acmebutchers.app.common.di.components.MainComponent;
import com.acmebutchers.app.common.util.LocationUtils;
import com.acmebutchers.app.common.util.UIUtils;
import com.acmebutchers.app.data.entity.LocationEntity;
import com.acmebutchers.app.domain.Place;
import com.acmebutchers.app.presentation.base.BaseFragment;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public class MapFragment extends BaseFragment implements MapMvpView, OnMapReadyCallback {

  private static final int REQUEST_LOCATION = 1;
  private static final float ZOOM = 14.0f;

  @Inject
  MapPresenter mapPresenter;

  private GoogleMap googleMap;
  private Unbinder unbinder;

  /**
   * Creates a new instance of {@link MapFragment}.
   */
  public static MapFragment newInstance() {
    return new MapFragment();
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    getComponent(MainComponent.class).inject(this);
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    View fragmentView = inflater.inflate(R.layout.fragment_map, container, false);
    unbinder = ButterKnife.bind(this, fragmentView);
    mapPresenter.attachView(this);
    return fragmentView;
  }

  @Override
  public void onStart() {
    super.onStart();
    askForPermissions();
  }

  @Override
  public void onDestroyView() {
    super.onDestroyView();
    unbinder.unbind();
  }

  @Override
  public void onDestroy() {
    super.onDestroy();
    mapPresenter.detachView();
  }

  @Override
  public void showLoading() {
    // Nothing to do
  }

  @Override
  public void hideLoading() {
    // Nothing to do
  }

  @Override
  public void showRetry() {
    // Nothing to do
  }

  @Override
  public void hideRetry() {
    // Nothing to do
  }

  @Override
  public void showError(String message) {
    UIUtils.showToastMessage(context(), message);
  }

  private void refreshMap() {
    // Load map
    SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map_fragment);
    if (mapFragment != null) {
      mapFragment.getMapAsync(this);
    }
  }

  /**
   * Shows the user location in the map
   */
  @SuppressLint("MissingPermission")
  private void displayMapLocation() {
    if (LocationUtils.isLocationEnabled(context())) {
      googleMap.setMyLocationEnabled(true);
    } else {
      // Show settings to enable location
      LocationUtils.showLocationSettings(context(), getView());
    }
  }

  /**
   * Request the location permission
   */
  private void requestLocationPermission() {
    requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
  }

  /**
   * Ask for permissions
   */
  private void askForPermissions() {
    // Check location permission
    if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager
        .PERMISSION_GRANTED) {
      refreshMap();
    } else {
      requestLocationPermission();
    }
  }

  private void loadButcherShops() {
    mapPresenter.loadButcherShops();
  }

  @Override
  public void showButcherShops(List<Place> shops) {
    // Add butcher shops as markers in the map
    for (Place shop : shops) {
      MarkerOptions marker = new MarkerOptions()
          .position(new LatLng(shop.location().latitude(), shop.location().longitude()))
          .title(shop.name());
      googleMap.addMarker(marker);
    }

    // Center map
    mapPresenter.centerMap();
  }

  @Override
  public void centerMap(LocationEntity location) {
    if (location != null) {
      LatLng latLng = new LatLng(location.latitude(), location.longitude());
      googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, ZOOM));
    }
  }

  @Override
  public void onMapReady(GoogleMap googleMap) {
    this.googleMap = googleMap;

    // Show current location
    displayMapLocation();

    // Load butcher shops
    loadButcherShops();
  }

  /**
   * Shows a dialog to request location permission
   */
  private void showDialog2RequestLocation() {
    if (shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION)) {
      // Provide an additional rationale to the user if the permission was not granted
      Snackbar.make(getView(), R.string.permission_location_rationale, Snackbar.LENGTH_INDEFINITE)
          .setAction(android.R.string.ok, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              requestLocationPermission();
            }
          }).show();
    } else {
      // User does not want to request permission again
      Snackbar.make(getView(), R.string.permission_location_not_granted, Snackbar.LENGTH_SHORT).show();
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public final void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
    if (requestCode == REQUEST_LOCATION && grantResults.length == 1) {
      if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
        refreshMap();
      }
      // Permission NOT granted
      else {
        showDialog2RequestLocation();
      }
    } else {
      super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
  }

  @Override
  public Context context() {
    return getActivity();
  }
}
