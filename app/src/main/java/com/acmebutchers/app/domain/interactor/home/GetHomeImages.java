package com.acmebutchers.app.domain.interactor.home;

import com.acmebutchers.app.common.executor.PostExecutionThread;
import com.acmebutchers.app.common.executor.ThreadExecutor;
import com.acmebutchers.app.domain.interactor.UseCase;
import com.acmebutchers.app.domain.repository.HomeRepository;

import javax.inject.Inject;

import rx.Observable;

public class GetHomeImages extends UseCase {

  private final HomeRepository homeRepository;

  @Inject
  public GetHomeImages(HomeRepository homeRepository, ThreadExecutor threadExecutor,
                       PostExecutionThread postExecutionThread) {
    super(threadExecutor, postExecutionThread);
    this.homeRepository = homeRepository;
  }

  @Override
  public Observable buildUseCaseObservable(Object... param) {
    return homeRepository.getImageUrls(null, null);
  }
}
