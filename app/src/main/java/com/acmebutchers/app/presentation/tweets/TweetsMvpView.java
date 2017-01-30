package com.acmebutchers.app.presentation.tweets;

import com.acmebutchers.app.presentation.base.MvpView;
import com.twitter.sdk.android.tweetui.SearchTimeline;

interface TweetsMvpView extends MvpView {

  /**
   * Shows nearby tweets related to meat is healthy
   *
   * @param timeline Twitter timeline
   */
  void showTweets(SearchTimeline timeline);
}
