package com.acmebutchers.app.presentation.map;

import com.acmebutchers.app.common.di.PerActivity;
import com.acmebutchers.app.presentation.base.BasePresenter;

@PerActivity
public class MapPresenter extends BasePresenter<MapMvpView> {

  /**
   * Initializes the presenter by start retrieving the shops.
   */
  void initialize() {
    checkViewAttached();
    loadMapShops();
  }

  /**
   * Load shops
   */
  private void loadMapShops() {
    MapMvpView mvpView = getMvpView();
    mvpView.showLoading();
    getMapShops();
  }

  private void getMapShops() {
    // TODO: Load shops in the map
  }
}
