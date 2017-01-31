package com.acmebutchers.app.common.di.modules;

import com.acmebutchers.app.common.di.PerActivity;
import com.acmebutchers.app.common.executor.PostExecutionThread;
import com.acmebutchers.app.common.executor.ThreadExecutor;
import com.acmebutchers.app.data.repository.TweetsDataRepository;
import com.acmebutchers.app.domain.interactor.UseCase;
import com.acmebutchers.app.domain.interactor.tweets.GetTweets;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

@Module
public class TweetsModule {

  @Provides
  @PerActivity
  @Named("tweets")
  public UseCase provideTweets(TweetsDataRepository tweetsDataRepository, ThreadExecutor
      threadExecutor, PostExecutionThread postExecutionThread) {
    return new GetTweets(tweetsDataRepository, threadExecutor, postExecutionThread);
  }
}
