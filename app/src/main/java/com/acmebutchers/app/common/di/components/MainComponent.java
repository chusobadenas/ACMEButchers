package com.acmebutchers.app.common.di.components;

import com.acmebutchers.app.common.di.PerActivity;
import com.acmebutchers.app.common.di.modules.ActivityModule;
import com.acmebutchers.app.common.di.modules.HomeModule;
import com.acmebutchers.app.common.di.modules.MapModule;
import com.acmebutchers.app.common.di.modules.TweetsModule;
import com.acmebutchers.app.presentation.main.MainFragment;
import com.acmebutchers.app.presentation.map.MapFragment;
import com.acmebutchers.app.presentation.tweets.TweetsFragment;

import dagger.Component;

/**
 * A scope {@link PerActivity} component.
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = {ActivityModule.class, HomeModule
    .class, MapModule.class, TweetsModule.class})
public interface MainComponent extends ActivityComponent {

  void inject(MainFragment mainFragment);

  void inject(MapFragment mapFragment);

  void inject(TweetsFragment tweetsFragment);
}
