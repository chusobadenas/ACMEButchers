package com.acmebutchers.app.presentation.map;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.acmebutchers.app.R;
import com.acmebutchers.app.common.di.components.MainComponent;
import com.acmebutchers.app.presentation.base.BaseFragment;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public class MapFragment extends BaseFragment implements MapMvpView, OnMapReadyCallback {

  @Inject
  MapPresenter mapPresenter;

  private GoogleMap googleMap;
  private Unbinder unbinder;

  /**
   * Creates a new instance of a MapFragment.
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
    loadMap();
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
    showToastMessage(message);
  }

  /**
   * Load map
   */
  private void loadMap() {
    mapPresenter.initialize();
  }

  @Override
  public void onMapReady(GoogleMap googleMap) {
    this.googleMap = googleMap;
  }

  @Override
  public Context context() {
    return getActivity();
  }
}
