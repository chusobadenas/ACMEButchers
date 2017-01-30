package com.acmebutchers.app.data.repository;

import android.content.Context;

import com.acmebutchers.app.BuildConfig;
import com.acmebutchers.app.data.entity.LocationEntity;
import com.acmebutchers.app.data.entity.response.PlaceGeometry;
import com.acmebutchers.app.data.entity.response.PlaceId;
import com.acmebutchers.app.data.entity.response.PlaceSearch;
import com.acmebutchers.app.data.repository.remote.GoogleApiService;
import com.acmebutchers.app.domain.Place;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;

import rx.Observable;
import rx.observers.TestSubscriber;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.when;

public class MapDataRepositoryTest {

  private static final LocationEntity LOCATION = LocationEntity.create(0.0, 1.0);
  private static final String KEYWORD = "Butcher shop";
  private static final Integer RADIUS = 500;

  private MapDataRepository mapDataRepository;

  @Mock
  private Context context;
  @Mock
  private GoogleApiService googleApiService;

  @Before
  public void setUp() {
    MockitoAnnotations.initMocks(this);
    mapDataRepository = new MapDataRepository(context, googleApiService);
  }

  private PlaceSearch createPlaceSearch() {
    PlaceGeometry placeGeometry = PlaceGeometry.create(LOCATION);
    PlaceId placeId = PlaceId.create("1", "Place one", placeGeometry);
    List<PlaceId> places = Collections.singletonList(placeId);
    return PlaceSearch.create(places);
  }

  @Test
  public void testGetButcherShopsSuccess() {
    // Create search
    Observable<PlaceSearch> apiPlaceSearch = Observable.just(createPlaceSearch());

    when(googleApiService.searchPlacesByKeyword(BuildConfig.GOOGLE_API_KEY, "0.0,1.0", RADIUS,
        KEYWORD)).thenReturn(apiPlaceSearch);

    Observable<List<Place>> observable = mapDataRepository.getPlaces(LOCATION);
    TestSubscriber<List<Place>> testSubscriber = new TestSubscriber<>();
    observable.subscribe(testSubscriber);

    testSubscriber.assertNoErrors();
    List<Place> shops = testSubscriber.getOnNextEvents().get(0);

    assertSame(shops.size(), 1);
    assertEquals(shops.get(0).name(), "Place one");
  }
}
