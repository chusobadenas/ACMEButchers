package com.acmebutchers.app.presentation.map;

import com.acmebutchers.app.R;
import com.acmebutchers.app.common.di.PerActivity;
import com.acmebutchers.app.common.exception.DefaultErrorBundle;
import com.acmebutchers.app.data.entity.LocationEntity;
import com.acmebutchers.app.domain.Place;
import com.acmebutchers.app.domain.interactor.DefaultSubscriber;
import com.acmebutchers.app.domain.interactor.UseCase;
import com.acmebutchers.app.presentation.base.BasePresenter;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import timber.log.Timber;

@PerActivity
public class MapPresenter extends BasePresenter<MapMvpView> {

  private final UseCase getButcherShopsUseCase;
  private final UseCase getCurrentLocationUseCase;

  @Inject
  public MapPresenter(@Named("butcherShops") UseCase getButcherShopsUseCase,
                      @Named("currentLocation") UseCase getCurrentLocationUseCase) {
    this.getButcherShopsUseCase = getButcherShopsUseCase;
    this.getCurrentLocationUseCase = getCurrentLocationUseCase;
  }

  @Override
  public void detachView() {
    super.detachView();
    getButcherShopsUseCase.unsubscribe();
    getCurrentLocationUseCase.unsubscribe();
  }

  /**
   * Load butcher shops
   */
  void loadButcherShops() {
    checkViewAttached();
    getButcherShops();
  }

  void centerMap() {
    checkViewAttached();
    getCurrentLocation();
  }

  private void getButcherShops() {
    getButcherShopsUseCase.execute(new ButcherShopsSubscriber());
  }

  private void getCurrentLocation() {
    getCurrentLocationUseCase.execute(new LocationSubscriber());
  }

  private final class ButcherShopsSubscriber extends DefaultSubscriber<List<Place>> {

    @Override
    public void onError(Throwable throwable) {
      // Create error
      MapMvpView mvpView = getMvpView();
      DefaultErrorBundle errorBundle = new DefaultErrorBundle(mvpView.context(), throwable,
          R.string.error_loading_shops);

      // Show error
      Timber.e(errorBundle.getException(), "There was an error loading the shops");
      showErrorMessage(errorBundle);
    }

    @Override
    public void onNext(List<Place> shops) {
      getMvpView().showButcherShops(shops);
    }
  }

  private final class LocationSubscriber extends DefaultSubscriber<LocationEntity> {

    @Override
    public void onError(Throwable throwable) {
      // Create error
      MapMvpView mvpView = getMvpView();
      DefaultErrorBundle errorBundle = new DefaultErrorBundle(mvpView.context(), throwable,
          R.string.error_loading_shops);

      // Show error
      Timber.e(errorBundle.getException(), "There was an error loading the shops");
      showErrorMessage(errorBundle);
    }

    @Override
    public void onNext(LocationEntity location) {
      getMvpView().centerMap(location);
    }
  }
}
