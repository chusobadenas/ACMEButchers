package com.acmebutchers.app.data.repository.remote;

import android.support.annotation.NonNull;

import com.acmebutchers.app.data.entity.response.Photos;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * APIService for retrieving data from the network using Retrofit.
 */
public interface APIService {

  String API_BASE_URL = "https://api.flickr.com/services/rest/";
  String FLICKR_API_KEY = "a667caf5c8f62e77887300cb406d8701";

  @GET
  Observable<Photos> searchPhotos(@NonNull @Query("method") String method,
                                  @NonNull @Query("api_key") String apiKey,
                                  @Query("text") String text,
                                  @Query("tags") String[] tags);

  /********
   * Helper class that sets up a new services
   *******/
  class Creator {

    private static OkHttpClient createHttpClient() {
      return new OkHttpClient.Builder()
          .readTimeout(10000, TimeUnit.MILLISECONDS)
          .connectTimeout(15000, TimeUnit.MILLISECONDS)
          .build();
    }

    public static APIService newAPIService() {
      Gson gson = new GsonBuilder()
          .setDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
          .create();
      Retrofit retrofit = new Retrofit.Builder()
          .baseUrl(API_BASE_URL)
          .client(Creator.createHttpClient())
          .addConverterFactory(GsonConverterFactory.create(gson))
          .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
          .build();
      return retrofit.create(APIService.class);
    }
  }
}
