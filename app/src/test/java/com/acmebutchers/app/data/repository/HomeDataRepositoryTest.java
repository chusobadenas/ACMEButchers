package com.acmebutchers.app.data.repository;

import com.acmebutchers.app.data.entity.response.PhotoId;
import com.acmebutchers.app.data.entity.response.PhotoSearch;
import com.acmebutchers.app.data.entity.response.Photos;
import com.acmebutchers.app.data.repository.remote.APIService;

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

public class HomeDataRepositoryTest {

  private static final String BUTCHER_SHOP = "Butcher shop";
  private static final String[] TAGS = {"meat"};

  private HomeDataRepository homeDataRepository;

  @Mock
  private APIService apiService;

  @Before
  public void setUp() {
    MockitoAnnotations.initMocks(this);
    homeDataRepository = new HomeDataRepository(apiService);
  }

  @Test
  public void testGetImageUrlsSuccess() {
    // Create search
    PhotoId photoId = PhotoId.create("1", "2", "3", "4", 5);
    Photos photos = Photos.create(1, 1, 20, "20", Collections.singletonList(photoId));
    PhotoSearch photoSearch = PhotoSearch.create(photos);
    Observable<PhotoSearch> apiPhotoSearch = Observable.just(photoSearch);

    when(apiService.searchPhotos(APIService.FLICKER_SEARCH_METHOD, APIService.FLICKR_API_KEY,
        APIService.JSON_FORMAT, APIService.NO_JSON_CALLBACK, BUTCHER_SHOP, TAGS))
        .thenReturn(apiPhotoSearch);

    Observable<List<String>> observable = homeDataRepository.getImageUrls(BUTCHER_SHOP, TAGS);
    TestSubscriber<List<String>> testSubscriber = new TestSubscriber<>();
    observable.subscribe(testSubscriber);

    testSubscriber.assertNoErrors();
    List<String> imageUrls = testSubscriber.getOnNextEvents().get(0);

    assertSame(imageUrls.size(), 1);
    assertEquals(imageUrls.get(0), "https://farm5.staticflickr.com/4/1_3.jpg");
  }
}
