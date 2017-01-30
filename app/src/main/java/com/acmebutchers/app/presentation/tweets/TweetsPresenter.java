package com.acmebutchers.app.presentation.tweets;

import com.acmebutchers.app.common.di.PerActivity;
import com.acmebutchers.app.presentation.base.BasePresenter;
import com.twitter.sdk.android.tweetui.SearchTimeline;

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
    getMvpView().showLoading();
    getTweets();
  }

  private void getTweets() {
    // Get tweets
    SearchTimeline timeline = new SearchTimeline.Builder()
        .query("meat+is+healthy")
        .build();

    // Show results
    getMvpView().showTweets(timeline);
  }
}
