package com.acmebutchers.app.domain.repository;

import java.util.List;

import rx.Observable;
import twitter4j.Status;

public interface TweetsRepository {

  /**
   * Get an {@link Observable} which will emit a list of tweets.
   */
  Observable<List<Status>> getTweets();
}
