package com.acmebutchers.app.common.di.modules;

import com.acmebutchers.app.common.executor.PostExecutionThread;
import com.acmebutchers.app.common.executor.ThreadExecutor;
import com.acmebutchers.app.data.repository.HomeDataRepository;
import com.acmebutchers.app.domain.interactor.UseCase;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.assertNotNull;

public class HomeModuleTest {

  private HomeModule homeModule;

  @Mock
  private HomeDataRepository homeDataRepository;
  @Mock
  private ThreadExecutor threadExecutor;
  @Mock
  private PostExecutionThread postExecutionThread;

  @Before
  public void setUp() {
    MockitoAnnotations.initMocks(this);
    homeModule = new HomeModule();
  }

  @Test
  public void testProvideGetHomeSuccess() {
    UseCase getHomeImages = homeModule.provideGetHomeImages(homeDataRepository, threadExecutor,
        postExecutionThread);
    assertNotNull(getHomeImages);
  }
}
