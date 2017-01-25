package com.acmebutchers.app.domain.repository;

import java.util.List;

import rx.Observable;

public interface HomeRepository {

  /**
   * Get an {@link rx.Observable} which will emit a list of image urls.
   */
  Observable<List<String>> getImageUrls(String text, String[] tags);
}
