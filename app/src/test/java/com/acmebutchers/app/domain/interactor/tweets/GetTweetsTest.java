package com.acmebutchers.app.domain.interactor.tweets;

import com.acmebutchers.app.common.executor.PostExecutionThread;
import com.acmebutchers.app.common.executor.ThreadExecutor;
import com.acmebutchers.app.domain.repository.TweetsRepository;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;

import rx.Observable;
import twitter4j.Status;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

public class GetTweetsTest {

  private GetTweets getTweets;

  @Mock
  private TweetsRepository tweetsRepository;
  @Mock
  private ThreadExecutor threadExecutor;
  @Mock
  private PostExecutionThread postExecutionThread;
  @Mock
  private Status tweet;

  @Before
  public void setUp() {
    MockitoAnnotations.initMocks(this);
    getTweets = new GetTweets(tweetsRepository, threadExecutor, postExecutionThread);
  }

  @Test
  public void testGetTweetsSuccess() {
    List<Status> tweets = Collections.singletonList(tweet);
    Observable<List<Status>> observable = Observable.just(tweets);
    when(tweetsRepository.getTweets()).thenReturn(observable);

    assertNotNull(getTweets.buildUseCaseObservable());

    verify(tweetsRepository).getTweets();
    verifyNoMoreInteractions(tweetsRepository);
    verifyZeroInteractions(threadExecutor);
    verifyZeroInteractions(postExecutionThread);
  }
}
