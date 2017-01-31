package com.acmebutchers.app.presentation.map;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.acmebutchers.app.R;
import com.acmebutchers.app.common.di.components.MainComponent;
import com.acmebutchers.app.common.util.UIUtils;
import com.acmebutchers.app.domain.Place;
import com.acmebutchers.app.presentation.base.BaseFragment;
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
  public void onResume() {
    super.onResume();
    refreshMap();
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
    SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
        .findFragmentById(R.id.map_fragment);
    if (mapFragment != null) {
      mapFragment.getMapAsync(this);
    }
  }

  /**
   * Shows the location button in the map
   */
  private void showCurrentLocation() {
    // Check location permission
    if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
        == PackageManager.PERMISSION_GRANTED) {
      googleMap.setMyLocationEnabled(true);
    }
    // Request location permission
    else {
      ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission
          .ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
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
  }

  @Override
  public void onMapReady(GoogleMap googleMap) {
    this.googleMap = googleMap;

    // Show location in map
    showCurrentLocation();

    // Load butcher shops
    loadButcherShops();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  @SuppressLint("MissingPermission")
  public final void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                               @NonNull int[] grantResults) {
    // Permission granted
    if (requestCode == REQUEST_LOCATION && grantResults.length == 1 && grantResults[0] ==
        PackageManager.PERMISSION_GRANTED) {
      googleMap.setMyLocationEnabled(true);
    } else {
      super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
  }

  @Override
  public Context context() {
    return getActivity();
  }
}
