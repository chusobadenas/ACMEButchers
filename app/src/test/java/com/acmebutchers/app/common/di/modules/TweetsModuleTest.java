package com.acmebutchers.app.common.di.modules;

import com.acmebutchers.app.common.executor.PostExecutionThread;
import com.acmebutchers.app.common.executor.ThreadExecutor;
import com.acmebutchers.app.data.repository.TweetsDataRepository;
import com.acmebutchers.app.domain.interactor.UseCase;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.assertNotNull;

public class TweetsModuleTest {

  private TweetsModule tweetsModule;

  @Mock
  private TweetsDataRepository tweetsDataRepository;
  @Mock
  private ThreadExecutor threadExecutor;
  @Mock
  private PostExecutionThread postExecutionThread;

  @Before
  public void setUp() {
    MockitoAnnotations.initMocks(this);
    tweetsModule = new TweetsModule();
  }

  @Test
  public void testProvideGetHomeSuccess() {
    UseCase getTweets = tweetsModule.provideTweets(tweetsDataRepository, threadExecutor,
        postExecutionThread);
    assertNotNull(getTweets);
  }
}
