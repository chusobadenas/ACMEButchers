package com.acmebutchers.app.data.repository.remote;

import android.support.annotation.NonNull;

import com.acmebutchers.app.data.entity.response.PhotoSearch;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Flickr Api Service for retrieving data from the network using Retrofit.
 */
public interface FlickrApiService {

  String API_BASE_URL = "https://api.flickr.com/services/";
  String API_VERSION = "rest";

  String FLICKR_API_KEY = "a667caf5c8f62e77887300cb406d8701";
  String FLICKR_SEARCH_METHOD = "flickr.photos.search";
  String JSON_FORMAT = "json";
  Integer NO_JSON_CALLBACK = 1;

  @GET(API_VERSION)
  Observable<PhotoSearch> searchPhotos(@NonNull @Query("method") String method,
                                       @NonNull @Query("api_key") String apiKey,
                                       @Query("format") String format,
                                       @Query("nojsoncallback") Integer noJsonCallback,
                                       @Query("text") String text,
                                       @Query("tags") String[] tags);
}
