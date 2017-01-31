package com.acmebutchers.app.presentation.tweets;

import com.acmebutchers.app.R;
import com.acmebutchers.app.common.di.PerActivity;
import com.acmebutchers.app.common.exception.DefaultErrorBundle;
import com.acmebutchers.app.domain.interactor.DefaultSubscriber;
import com.acmebutchers.app.domain.interactor.UseCase;
import com.acmebutchers.app.presentation.base.BasePresenter;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import timber.log.Timber;
import twitter4j.Status;

@PerActivity
public class TweetsPresenter extends BasePresenter<TweetsMvpView> {

  private final UseCase getTweetsUseCase;

  @Inject
  public TweetsPresenter(@Named("tweets") UseCase getTweetsUseCase) {
    this.getTweetsUseCase = getTweetsUseCase;
  }

  @Override
  public void detachView() {
    super.detachView();
    getTweetsUseCase.unsubscribe();
  }

  /**
   * Load tweets
   */
  void loadTweets() {
    checkViewAttached();
    getTweets();
  }

  private void getTweets() {
    getTweetsUseCase.execute(new TweetsSubscriber());
  }

  private final class TweetsSubscriber extends DefaultSubscriber<List<Status>> {

    @Override
    public void onError(Throwable throwable) {
      // Create error
      TweetsMvpView mvpView = getMvpView();
      DefaultErrorBundle errorBundle = new DefaultErrorBundle(mvpView.context(), throwable,
          R.string.error_loading_tweets);

      // Show error
      Timber.e(errorBundle.getException(), "There was an error loading the tweets");
      showErrorMessage(errorBundle);
    }

    @Override
    public void onNext(List<Status> tweets) {
      getMvpView().showTweets(tweets);
    }
  }
}
