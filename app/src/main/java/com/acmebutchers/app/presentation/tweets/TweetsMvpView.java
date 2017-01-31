package com.acmebutchers.app.presentation.tweets;

import com.acmebutchers.app.presentation.base.MvpView;

import java.util.List;

import twitter4j.Status;

interface TweetsMvpView extends MvpView {

  /**
   * Shows nearby tweets related to meat
   *
   * @param tweets the list of tweets
   */
  void showTweets(List<Status> tweets);
}
