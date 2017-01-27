package com.acmebutchers.app.domain.repository;

import com.acmebutchers.app.domain.Place;

import java.util.List;

import rx.Observable;

public interface MapRepository {

  /**
   * Get an {@link Observable} which will emit a list of butcher shops.
   */
  Observable<List<Place>> getButcherShops();
}
