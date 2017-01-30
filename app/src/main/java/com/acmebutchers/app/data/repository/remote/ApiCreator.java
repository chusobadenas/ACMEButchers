package com.acmebutchers.app.data.repository.remote;

import com.acmebutchers.app.BuildConfig;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public final class ApiCreator {

  private ApiCreator() {
    // Empty constructor
  }

  private static OkHttpClient createHttpClient() {
    OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder();

    // Enable logging
    if (BuildConfig.DEBUG) {
      HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
      interceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);
      clientBuilder.addInterceptor(interceptor);
    }

    return clientBuilder
        .readTimeout(10000, TimeUnit.MILLISECONDS)
        .connectTimeout(15000, TimeUnit.MILLISECONDS)
        .build();
  }

  private static Retrofit createRetrofit(String baseUrl) {
    Gson gson = new GsonBuilder()
        .setDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
        .registerTypeAdapterFactory(GsonAdapterFactory.create())
        .create();

    return new Retrofit.Builder()
        .baseUrl(baseUrl)
        .client(ApiCreator.createHttpClient())
        .addConverterFactory(GsonConverterFactory.create(gson))
        .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
        .build();
  }

  /**
   * Creates a new {@link FlickrApiService}
   *
   * @param baseUrl Flicker base url
   * @return a new {@link FlickrApiService}
   */
  public static FlickrApiService newFlickrApiService(String baseUrl) {
    return createRetrofit(baseUrl).create(FlickrApiService.class);
  }

  /**
   * Creates a new {@link GoogleApiService}
   *
   * @param baseUrl Google base url
   * @return a new {@link GoogleApiService}
   */
  public static GoogleApiService newGoogleApiService(String baseUrl) {
    return createRetrofit(baseUrl).create(GoogleApiService.class);
  }
}
