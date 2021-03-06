package com.acmebutchers.app.common.di.modules;

import android.content.Context;

import com.acmebutchers.app.AndroidApplication;
import com.acmebutchers.app.BuildConfig;
import com.acmebutchers.app.common.di.ApplicationContext;
import com.acmebutchers.app.common.executor.JobExecutor;
import com.acmebutchers.app.common.executor.PostExecutionThread;
import com.acmebutchers.app.common.executor.ThreadExecutor;
import com.acmebutchers.app.common.executor.UIThread;
import com.acmebutchers.app.data.repository.HomeDataRepository;
import com.acmebutchers.app.data.repository.MapDataRepository;
import com.acmebutchers.app.data.repository.TweetsDataRepository;
import com.acmebutchers.app.data.repository.remote.ApiCreator;
import com.acmebutchers.app.data.repository.remote.FlickrApiService;
import com.acmebutchers.app.data.repository.remote.GoogleApiService;
import com.acmebutchers.app.domain.repository.HomeRepository;
import com.acmebutchers.app.domain.repository.MapRepository;
import com.acmebutchers.app.domain.repository.TweetsRepository;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

/**
 * Dagger module that provides objects which will live during the application lifecycle.
 */
@Module
public class ApplicationModule {

  private final AndroidApplication application;

  /**
   * Constructor
   *
   * @param application the application
   */
  public ApplicationModule(AndroidApplication application) {
    this.application = application;
  }

  @Provides
  @ApplicationContext
  @Singleton
  Context provideApplicationContext() {
    return application;
  }

  @Provides
  @Singleton
  ThreadExecutor provideThreadExecutor(JobExecutor jobExecutor) {
    return jobExecutor;
  }

  @Provides
  @Singleton
  PostExecutionThread providePostExecutionThread(UIThread uiThread) {
    return uiThread;
  }

  @Provides
  @Singleton
  FlickrApiService provideFlickrApiService() {
    return ApiCreator.newFlickrApiService(FlickrApiService.API_BASE_URL);
  }

  @Provides
  @Singleton
  GoogleApiService provideGoogleApiService() {
    return ApiCreator.newGoogleApiService(GoogleApiService.API_BASE_URL);
  }

  @Provides
  @Singleton
  HomeRepository provideHomeRepository(HomeDataRepository homeDataRepository) {
    return homeDataRepository;
  }

  @Provides
  @Singleton
  MapRepository provideMapRepository(MapDataRepository mapDataRepository) {
    return mapDataRepository;
  }

  @Provides
  @Singleton
  TweetsRepository provideTweetsRepository(TweetsDataRepository tweetsDataRepository) {
    return tweetsDataRepository;
  }

  @Provides
  @Singleton
  Twitter provideTwitter() {
    ConfigurationBuilder configBuilder = new ConfigurationBuilder();
    configBuilder.setOAuthConsumerKey(BuildConfig.TWITTER_API_KEY)
        .setOAuthConsumerSecret(BuildConfig.TWITTER_API_SECRET)
        .setOAuthAccessToken(BuildConfig.TWITTER_ACCESS_TOKEN)
        .setOAuthAccessTokenSecret(BuildConfig.TWITTER_ACCESS_TOKEN_SECRET);

    TwitterFactory twitterFactory = new TwitterFactory(configBuilder.build());
    return twitterFactory.getInstance();
  }
}
