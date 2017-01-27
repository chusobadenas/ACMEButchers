package com.acmebutchers.app.presentation.map;

import com.acmebutchers.app.domain.Place;
import com.acmebutchers.app.presentation.base.MvpView;

import java.util.List;

interface MapMvpView extends MvpView {

  /**
   * Shows nearby butcher shops
   *
   * @param shops the list of shops
   */
  void showButcherShops(List<Place> shops);
}
