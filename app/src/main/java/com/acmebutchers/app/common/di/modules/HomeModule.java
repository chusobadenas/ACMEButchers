package com.acmebutchers.app.common.di.modules;

import com.acmebutchers.app.common.di.PerActivity;
import com.acmebutchers.app.common.executor.PostExecutionThread;
import com.acmebutchers.app.common.executor.ThreadExecutor;
import com.acmebutchers.app.data.repository.HomeDataRepository;
import com.acmebutchers.app.domain.interactor.UseCase;
import com.acmebutchers.app.domain.interactor.home.GetHomeImages;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

@Module
public class HomeModule {

  @Provides
  @PerActivity
  @Named("homeImages")
  public UseCase provideGetHomeImages(HomeDataRepository homeDataRepository, ThreadExecutor
      threadExecutor, PostExecutionThread postExecutionThread) {
    return new GetHomeImages(homeDataRepository, threadExecutor, postExecutionThread);
  }
}
