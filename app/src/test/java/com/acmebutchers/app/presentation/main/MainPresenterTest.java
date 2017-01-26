package com.acmebutchers.app.presentation.main;

import com.acmebutchers.app.domain.interactor.DefaultSubscriber;
import com.acmebutchers.app.domain.interactor.home.GetHomeImages;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;

public class MainPresenterTest {

  private MainPresenter mMainPresenter;

  @Mock
  private GetHomeImages getHomeImages;
  @Mock
  private MainMvpView mainMvpView;

  @Before
  public void setUp() {
    MockitoAnnotations.initMocks(this);
    mMainPresenter = new MainPresenter(getHomeImages);
    mMainPresenter.attachView(mainMvpView);
  }

  @Test
  public void testAttachViewSuccess() {
    assertNotNull(mMainPresenter.getMvpView());
  }

  @Test
  public void testDetachViewSuccess() {
    mMainPresenter.detachView();
    assertNull(mMainPresenter.getMvpView());
    verify(getHomeImages).unsubscribe();
  }

  @Test
  public void testInitializeSuccess() {
    mMainPresenter.initialize();
    verify(getHomeImages).execute(any(DefaultSubscriber.class));
  }
}
