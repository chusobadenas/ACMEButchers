package com.acmebutchers.app.domain.interactor.tweets;

import com.acmebutchers.app.common.executor.PostExecutionThread;
import com.acmebutchers.app.common.executor.ThreadExecutor;
import com.acmebutchers.app.domain.interactor.UseCase;
import com.acmebutchers.app.domain.repository.TweetsRepository;

import javax.inject.Inject;

import rx.Observable;

public class GetTweets extends UseCase {

  private final TweetsRepository tweetsRepository;

  @Inject
  public GetTweets(TweetsRepository tweetsRepository, ThreadExecutor threadExecutor,
                   PostExecutionThread postExecutionThread) {
    super(threadExecutor, postExecutionThread);
    this.tweetsRepository = tweetsRepository;
  }

  @Override
  public Observable buildUseCaseObservable(Object... param) {
    return tweetsRepository.getTweets();
  }
}
