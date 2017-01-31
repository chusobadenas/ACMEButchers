package com.acmebutchers.app.presentation.map;

import com.acmebutchers.app.domain.interactor.DefaultSubscriber;
import com.acmebutchers.app.domain.interactor.map.GetButcherShops;
import com.acmebutchers.app.domain.interactor.map.GetCurrentLocation;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;

public class MapPresenterTest {

  private MapPresenter mapPresenter;

  @Mock
  private GetButcherShops getButcherShops;
  @Mock
  private GetCurrentLocation getCurrentLocation;
  @Mock
  private MapMvpView mapMvpView;

  @Before
  public void setUp() {
    MockitoAnnotations.initMocks(this);
    mapPresenter = new MapPresenter(getButcherShops, getCurrentLocation);
    mapPresenter.attachView(mapMvpView);
  }

  @Test
  public void testAttachViewSuccess() {
    assertNotNull(mapPresenter.getMvpView());
  }

  @Test
  public void testDetachViewSuccess() {
    mapPresenter.detachView();
    assertNull(mapPresenter.getMvpView());
    verify(getButcherShops).unsubscribe();
    verify(getCurrentLocation).unsubscribe();
  }

  @Test
  public void testLoadButcherShopsSuccess() {
    mapPresenter.loadButcherShops();
    verify(getButcherShops).execute(any(DefaultSubscriber.class));
  }

  @Test
  public void testCenterMapSuccess() {
    mapPresenter.centerMap();
    verify(getCurrentLocation).execute(any(DefaultSubscriber.class));
  }
}
