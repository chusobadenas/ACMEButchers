package com.acmebutchers.app.domain.interactor.map;

import com.acmebutchers.app.common.executor.PostExecutionThread;
import com.acmebutchers.app.common.executor.ThreadExecutor;
import com.acmebutchers.app.data.entity.LocationEntity;
import com.acmebutchers.app.domain.Place;
import com.acmebutchers.app.domain.repository.MapRepository;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;

import rx.Observable;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

public class GetButcherShopsTest {

  private GetButcherShops getButcherShops;

  @Mock
  private MapRepository mapRepository;
  @Mock
  private ThreadExecutor threadExecutor;
  @Mock
  private PostExecutionThread postExecutionThread;

  @Before
  public void setUp() {
    MockitoAnnotations.initMocks(this);
    getButcherShops = new GetButcherShops(mapRepository, threadExecutor, postExecutionThread);
  }

  @Test
  public void testGetButcherShopsSuccess() {
    LocationEntity locationEntity = LocationEntity.create(0.0, 0.0);
    Place place = Place.create("Butcher test shop", locationEntity);
    List<Place> places = Collections.singletonList(place);
    Observable<List<Place>> observable = Observable.just(places);
    when(mapRepository.getButcherShops()).thenReturn(observable);

    assertNotNull(getButcherShops.buildUseCaseObservable());

    verify(mapRepository).getButcherShops();
    verifyNoMoreInteractions(mapRepository);
    verifyZeroInteractions(threadExecutor);
    verifyZeroInteractions(postExecutionThread);
  }
}
