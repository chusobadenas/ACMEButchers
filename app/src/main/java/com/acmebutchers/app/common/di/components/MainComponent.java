package com.acmebutchers.app.common.di.components;

import com.acmebutchers.app.common.di.PerActivity;
import com.acmebutchers.app.common.di.modules.ActivityModule;
import com.acmebutchers.app.common.di.modules.HomeModule;
import com.acmebutchers.app.presentation.main.MainFragment;

import dagger.Component;

/**
 * A scope {@link PerActivity} component.
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = {ActivityModule.class, HomeModule
    .class})
public interface MainComponent extends ActivityComponent {

  void inject(MainFragment mainFragment);
}
