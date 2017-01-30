package com.acmebutchers.app;

import android.app.Application;

import com.acmebutchers.app.common.di.HasComponent;
import com.acmebutchers.app.common.di.components.ApplicationComponent;
import com.acmebutchers.app.common.di.components.DaggerApplicationComponent;
import com.acmebutchers.app.common.di.modules.ApplicationModule;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.tweetui.TweetUi;

import io.fabric.sdk.android.Fabric;
import timber.log.Timber;

public class AndroidApplication extends Application implements HasComponent<ApplicationComponent> {

  private ApplicationComponent applicationComponent;

  @Override
  public void onCreate() {
    super.onCreate();
    initializeInjector();
    initializeTimber();
    initializeTwitter();
  }

  @Override
  public ApplicationComponent getComponent() {
    return applicationComponent;
  }

  private void initializeInjector() {
    applicationComponent = DaggerApplicationComponent.builder()
        .applicationModule(new ApplicationModule(this))
        .build();
  }

  private void initializeTimber() {
    if (BuildConfig.DEBUG) {
      Timber.plant(new Timber.DebugTree());
    }
  }

  protected void initializeTwitter() {
    TwitterAuthConfig authConfig = new TwitterAuthConfig(BuildConfig.TWITTER_KEY, BuildConfig
        .TWITTER_SECRET);
    Fabric.with(this, new TwitterCore(authConfig), new TweetUi());
  }
}
