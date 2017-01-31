package com.acmebutchers.app.presentation.main;

import com.acmebutchers.app.R;
import com.acmebutchers.app.common.di.PerActivity;
import com.acmebutchers.app.common.exception.DefaultErrorBundle;
import com.acmebutchers.app.domain.interactor.DefaultSubscriber;
import com.acmebutchers.app.domain.interactor.UseCase;
import com.acmebutchers.app.presentation.base.BasePresenter;
import com.acmebutchers.app.presentation.base.Presenter;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import timber.log.Timber;

/**
 * {@link Presenter} that controls communication between views and models of the presentation layer.
 */
@PerActivity
public class MainPresenter extends BasePresenter<MainMvpView> {

  private final UseCase getHomeImagesUseCase;

  @Inject
  public MainPresenter(@Named("homeImages") UseCase getHomeImagesUseCase) {
    this.getHomeImagesUseCase = getHomeImagesUseCase;
  }

  @Override
  public void detachView() {
    super.detachView();
    getHomeImagesUseCase.unsubscribe();
  }

  /**
   * Initializes the presenter by start retrieving home images.
   */
  void initialize() {
    checkViewAttached();
    loadImages();
  }

  /**
   * Loads home images
   */
  private void loadImages() {
    getMvpView().showLoading();
    getHomeImages();
  }

  private void getHomeImages() {
    getHomeImagesUseCase.execute(new HomeImagesSubscriber());
  }

  private final class HomeImagesSubscriber extends DefaultSubscriber<List<String>> {

    @Override
    public void onError(Throwable throwable) {
      // Create error
      MainMvpView mvpView = getMvpView();
      DefaultErrorBundle errorBundle = new DefaultErrorBundle(mvpView.context(), throwable,
          R.string.error_loading_images);

      // Show error
      Timber.e(errorBundle.getException(), "There was an error loading the images");
      mvpView.hideLoading();
      showErrorMessage(errorBundle);
    }

    @Override
    public void onNext(List<String> imageUrls) {
      MainMvpView mvpView = getMvpView();
      mvpView.hideLoading();

      // Set values in the view
      mvpView.displayHomeImages(imageUrls);
    }
  }
}
