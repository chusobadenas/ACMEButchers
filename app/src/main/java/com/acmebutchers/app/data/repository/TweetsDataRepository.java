package com.acmebutchers.app.data.repository;

import com.acmebutchers.app.data.entity.LocationEntity;
import com.acmebutchers.app.domain.repository.MapRepository;
import com.acmebutchers.app.domain.repository.TweetsRepository;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.functions.Func1;
import twitter4j.GeoLocation;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;

public class TweetsDataRepository implements TweetsRepository {

  private static final String QUERY = "meat is healthy";
  private static final Double RADIUS = 30.0;

  private final MapRepository mapRepository;
  private final Twitter twitter;

  @Inject
  public TweetsDataRepository(MapRepository mapRepository, Twitter twitter) {
    this.mapRepository = mapRepository;
    this.twitter = twitter;
  }

  @Override
  public Observable<List<Status>> getTweets() {
    return mapRepository.getLocation().flatMap(new Func1<LocationEntity, Observable<List<Status>>>() {
      @Override
      public Observable<List<Status>> call(LocationEntity locationEntity) {
        List<Status> tweets;
        Double latitude = locationEntity.latitude();
        Double longitude = locationEntity.longitude();

        try {
          // Search tweets
          Query query = new Query();
          query.setQuery(QUERY);
          query.setGeoCode(new GeoLocation(latitude, longitude), RADIUS, Query.Unit.km);

          // Get tweets
          QueryResult queryResult = twitter.search(query);
          tweets = queryResult.getTweets();

        } catch (TwitterException exception) {
          return Observable.error(exception);
        }

        return Observable.just(tweets);
      }
    });
  }
}
