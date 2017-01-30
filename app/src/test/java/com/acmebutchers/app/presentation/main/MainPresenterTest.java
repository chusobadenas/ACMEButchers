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

  private MainPresenter mainPresenter;

  @Mock
  private GetHomeImages getHomeImages;
  @Mock
  private MainMvpView mainMvpView;

  @Before
  public void setUp() {
    MockitoAnnotations.initMocks(this);
    mainPresenter = new MainPresenter(getHomeImages);
    mainPresenter.attachView(mainMvpView);
  }

  @Test
  public void testAttachViewSuccess() {
    assertNotNull(mainPresenter.getMvpView());
  }

  @Test
  public void testDetachViewSuccess() {
    mainPresenter.detachView();
    assertNull(mainPresenter.getMvpView());
    verify(getHomeImages).unsubscribe();
  }

  @Test
  public void testInitializeSuccess() {
    mainPresenter.initialize();
    verify(getHomeImages).execute(any(DefaultSubscriber.class));
  }
}
