package com.acmebutchers.app.domain.interactor.map;

import com.acmebutchers.app.common.executor.PostExecutionThread;
import com.acmebutchers.app.common.executor.ThreadExecutor;
import com.acmebutchers.app.data.entity.LocationEntity;
import com.acmebutchers.app.domain.repository.MapRepository;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import rx.Observable;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

public class GetCurrentLocationTest {

  private GetCurrentLocation getCurrentLocation;

  @Mock
  private MapRepository mapRepository;
  @Mock
  private ThreadExecutor threadExecutor;
  @Mock
  private PostExecutionThread postExecutionThread;

  @Before
  public void setUp() {
    MockitoAnnotations.initMocks(this);
    getCurrentLocation = new GetCurrentLocation(mapRepository, threadExecutor, postExecutionThread);
  }

  @Test
  public void testGetCurrentLocationSuccess() {
    LocationEntity location = LocationEntity.create(0.0, 0.0);
    Observable<LocationEntity> observable = Observable.just(location);
    when(mapRepository.getLocation()).thenReturn(observable);

    assertNotNull(getCurrentLocation.buildUseCaseObservable());

    verify(mapRepository).getLocation();
    verifyNoMoreInteractions(mapRepository);
    verifyZeroInteractions(threadExecutor);
    verifyZeroInteractions(postExecutionThread);
  }
}
