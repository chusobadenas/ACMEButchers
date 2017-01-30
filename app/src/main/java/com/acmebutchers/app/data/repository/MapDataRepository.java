package com.acmebutchers.app.data.repository;

import android.content.Context;
import android.location.Location;

import com.acmebutchers.app.BuildConfig;
import com.acmebutchers.app.common.di.ApplicationContext;
import com.acmebutchers.app.data.entity.LocationEntity;
import com.acmebutchers.app.data.entity.response.PlaceId;
import com.acmebutchers.app.data.entity.response.PlaceSearch;
import com.acmebutchers.app.data.repository.remote.GoogleApiService;
import com.acmebutchers.app.domain.Place;
import com.acmebutchers.app.domain.repository.MapRepository;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import pl.charmas.android.reactivelocation.ReactiveLocationProvider;
import rx.Observable;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class MapDataRepository implements MapRepository {

  private static final String KEYWORD = "Butcher shop";
  private static final Integer RADIUS = 500;

  private final Context context;
  private final GoogleApiService googleApiService;

  @Inject
  public MapDataRepository(@ApplicationContext Context context, GoogleApiService googleApiService) {
    this.context = context;
    this.googleApiService = googleApiService;
  }

  private Observable<LocationEntity> getLocation() {
    ReactiveLocationProvider locationProvider = new ReactiveLocationProvider(context);
    return locationProvider.getLastKnownLocation()
        .map(new Func1<Location, LocationEntity>() {
          @Override
          public LocationEntity call(Location location) {
            return LocationEntity.create(location.getLatitude(), location.getLongitude());
          }
        })
        .observeOn(Schedulers.io());
  }

  private Observable<PlaceSearch> searchPlaces(LocationEntity locationEntity, Integer radius,
                                               String keyword) {
    String location = "" + locationEntity.latitude() + ',' + locationEntity.longitude();
    return googleApiService.searchPlacesByKeyword(BuildConfig.GOOGLE_API_KEY, location, radius,
        keyword);
  }

  private Observable<List<Place>> getPlaces(LocationEntity locationEntity) {
    return searchPlaces(locationEntity, RADIUS, KEYWORD)
        .map(new Func1<PlaceSearch, List<Place>>() {
          @Override
          public List<Place> call(PlaceSearch placeSearch) {
            List<Place> shops = new ArrayList<>();

            for (PlaceId placeId : placeSearch.results()) {
              String placeName = placeId.name();
              LocationEntity placeLocation = placeId.geometry().location();
              Place place = Place.create(placeName, placeLocation);
              shops.add(place);
            }

            return shops;
          }
        });
  }

  @Override
  public Observable<List<Place>> getButcherShops() {
    return getLocation().flatMap(new Func1<LocationEntity, Observable<List<Place>>>() {
      @Override
      public Observable<List<Place>> call(LocationEntity locationEntity) {
        return getPlaces(locationEntity);
      }
    });
  }
}
