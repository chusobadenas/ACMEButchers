package com.acmebutchers.app.common.di.modules;

import com.acmebutchers.app.common.di.PerActivity;
import com.acmebutchers.app.common.executor.PostExecutionThread;
import com.acmebutchers.app.common.executor.ThreadExecutor;
import com.acmebutchers.app.data.repository.MapDataRepository;
import com.acmebutchers.app.domain.interactor.UseCase;
import com.acmebutchers.app.domain.interactor.map.GetButcherShops;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

@Module
public class MapModule {

  @Provides
  @PerActivity
  @Named("butcherShops")
  public UseCase provideGetButcherShops(MapDataRepository mapDataRepository, ThreadExecutor
      threadExecutor, PostExecutionThread postExecutionThread) {
    return new GetButcherShops(mapDataRepository, threadExecutor, postExecutionThread);
  }
}
