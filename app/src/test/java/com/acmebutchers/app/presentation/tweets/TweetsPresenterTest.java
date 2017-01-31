package com.acmebutchers.app.presentation.tweets;

import com.acmebutchers.app.domain.interactor.DefaultSubscriber;
import com.acmebutchers.app.domain.interactor.tweets.GetTweets;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;

public class TweetsPresenterTest {

  private TweetsPresenter tweetsPresenter;

  @Mock
  private GetTweets getTweets;
  @Mock
  private TweetsMvpView tweetsMvpView;

  @Before
  public void setUp() {
    MockitoAnnotations.initMocks(this);
    tweetsPresenter = new TweetsPresenter(getTweets);
    tweetsPresenter.attachView(tweetsMvpView);
  }

  @Test
  public void testAttachViewSuccess() {
    assertNotNull(tweetsPresenter.getMvpView());
  }

  @Test
  public void testDetachViewSuccess() {
    tweetsPresenter.detachView();
    assertNull(tweetsPresenter.getMvpView());
    verify(getTweets).unsubscribe();
  }

  @Test
  public void testInitializeSuccess() {
    tweetsPresenter.loadTweets();
    verify(getTweets).execute(any(DefaultSubscriber.class));
  }
}
