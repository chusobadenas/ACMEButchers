package com.acmebutchers.app.presentation.tweets;

import com.acmebutchers.app.presentation.base.MvpView;

interface TweetsMvpView extends MvpView {

  /**
   * Shows nearby tweets related to meat is healthy
   */
  void showTweets();
}
