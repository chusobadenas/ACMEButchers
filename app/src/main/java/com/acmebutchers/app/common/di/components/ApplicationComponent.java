package com.acmebutchers.app.common.di.components;

import android.content.Context;

import com.acmebutchers.app.common.di.ApplicationContext;
import com.acmebutchers.app.common.di.modules.ApplicationModule;
import com.acmebutchers.app.common.executor.PostExecutionThread;
import com.acmebutchers.app.common.executor.ThreadExecutor;
import com.acmebutchers.app.data.repository.remote.APIService;
import com.acmebutchers.app.presentation.base.BaseActivity;
import com.acmebutchers.app.presentation.navigation.Navigator;

import javax.inject.Singleton;

import dagger.Component;

/**
 * A component whose lifetime is the life of the application.
 */
@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {

  void inject(BaseActivity baseActivity);

  // Exposed to sub-graphs
  @ApplicationContext
  Context context();

  ThreadExecutor threadExecutor();

  PostExecutionThread postExecutionThread();

  APIService apiService();

  Navigator navigator();
}