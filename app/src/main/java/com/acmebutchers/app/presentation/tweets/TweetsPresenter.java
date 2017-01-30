package com.acmebutchers.app.presentation.tweets;

import com.acmebutchers.app.common.di.PerActivity;
import com.acmebutchers.app.presentation.base.BasePresenter;

import javax.inject.Inject;

@PerActivity
public class TweetsPresenter extends BasePresenter<TweetsMvpView> {

  @Inject
  public TweetsPresenter() {
    // Empty constructor
  }

  /**
   * Load tweets
   */
  void loadTweets() {
    checkViewAttached();
    getTweets();
  }

  private void getTweets() {
    // TODO
    getMvpView().showTweets();
  }
}
