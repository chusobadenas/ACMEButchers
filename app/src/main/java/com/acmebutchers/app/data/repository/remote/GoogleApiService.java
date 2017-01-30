package com.acmebutchers.app.data.repository.remote;

import android.support.annotation.NonNull;

import com.acmebutchers.app.data.entity.response.PlaceSearch;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Google Api Service for retrieving data from the network using Retrofit.
 */
public interface GoogleApiService {

  String API_BASE_URL = "https://maps.googleapis.com/maps/api/place/nearbysearch/";

  @GET("json")
  Observable<PlaceSearch> searchPlacesByKeyword(@NonNull @Query("key") String apiKey,
                                                @NonNull @Query("location") String location,
                                                @NonNull @Query("radius") Integer radius,
                                                @Query("keyword") String keyword);
}
