package com.acmebutchers.app.common.di.modules;

import com.acmebutchers.app.common.executor.PostExecutionThread;
import com.acmebutchers.app.common.executor.ThreadExecutor;
import com.acmebutchers.app.data.repository.MapDataRepository;
import com.acmebutchers.app.domain.interactor.UseCase;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.assertNotNull;

public class MapModuleTest {

  private MapModule mapModule;

  @Mock
  private MapDataRepository mapDataRepository;
  @Mock
  private ThreadExecutor threadExecutor;
  @Mock
  private PostExecutionThread postExecutionThread;

  @Before
  public void setUp() {
    MockitoAnnotations.initMocks(this);
    mapModule = new MapModule();
  }

  @Test
  public void testProvideGetHomeSuccess() {
    UseCase getButcherShops = mapModule.provideGetButcherShops(mapDataRepository, threadExecutor,
        postExecutionThread);
    assertNotNull(getButcherShops);
  }
}
