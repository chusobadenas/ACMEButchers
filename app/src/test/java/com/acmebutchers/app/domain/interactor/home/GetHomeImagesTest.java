package com.acmebutchers.app.domain.interactor.home;

import com.acmebutchers.app.common.executor.PostExecutionThread;
import com.acmebutchers.app.common.executor.ThreadExecutor;
import com.acmebutchers.app.domain.repository.HomeRepository;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import rx.Observable;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

public class GetHomeImagesTest {

  private static final String BUTCHER_SHOP = "Butcher shop";
  private static final String[] TAGS = {"meat"};

  private GetHomeImages getHomeImages;

  @Mock
  private HomeRepository homeRepository;
  @Mock
  private ThreadExecutor threadExecutor;
  @Mock
  private PostExecutionThread postExecutionThread;

  @Before
  public void setUp() {
    MockitoAnnotations.initMocks(this);
    getHomeImages = new GetHomeImages(homeRepository, threadExecutor, postExecutionThread);
  }

  @Test
  public void testGetHomeImagesSuccess() {
    List<String> images = Arrays.asList("img1", "img2");
    Observable<List<String>> observable = Observable.just(images);
    when(homeRepository.getImageUrls(BUTCHER_SHOP, TAGS)).thenReturn(observable);

    assertNotNull(getHomeImages.buildUseCaseObservable());

    verify(homeRepository).getImageUrls(BUTCHER_SHOP, TAGS);
    verifyNoMoreInteractions(homeRepository);
    verifyZeroInteractions(threadExecutor);
    verifyZeroInteractions(postExecutionThread);
  }
}
