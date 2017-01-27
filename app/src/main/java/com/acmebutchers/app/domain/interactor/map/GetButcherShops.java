package com.acmebutchers.app.domain.interactor.map;

import com.acmebutchers.app.common.executor.PostExecutionThread;
import com.acmebutchers.app.common.executor.ThreadExecutor;
import com.acmebutchers.app.domain.interactor.UseCase;
import com.acmebutchers.app.domain.repository.MapRepository;

import javax.inject.Inject;

import rx.Observable;

public class GetButcherShops extends UseCase {

  private final MapRepository mapRepository;

  @Inject
  public GetButcherShops(MapRepository mapRepository, ThreadExecutor threadExecutor,
                         PostExecutionThread postExecutionThread) {
    super(threadExecutor, postExecutionThread);
    this.mapRepository = mapRepository;
  }

  @Override
  public Observable buildUseCaseObservable(Object... param) {
    return mapRepository.getButcherShops();
  }
}
