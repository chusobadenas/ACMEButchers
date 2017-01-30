package com.acmebutchers.app.presentation.tweets;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.acmebutchers.app.R;
import com.acmebutchers.app.common.di.components.MainComponent;
import com.acmebutchers.app.presentation.base.BaseFragment;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public class TweetsFragment extends BaseFragment implements TweetsMvpView {

  @Inject
  TweetsPresenter tweetsPresenter;

  private Unbinder unbinder;

  /**
   * Creates a new instance of {@link TweetsFragment}.
   */
  public static TweetsFragment newInstance() {
    return new TweetsFragment();
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    getComponent(MainComponent.class).inject(this);
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    View fragmentView = inflater.inflate(R.layout.fragment_tweets, container, false);
    unbinder = ButterKnife.bind(this, fragmentView);
    tweetsPresenter.attachView(this);
    return fragmentView;
  }

  @Override
  public void onResume() {
    super.onResume();
    loadTweets();
  }

  @Override
  public void onDestroyView() {
    super.onDestroyView();
    unbinder.unbind();
  }

  @Override
  public void onDestroy() {
    super.onDestroy();
    tweetsPresenter.detachView();
  }

  @Override
  public void showLoading() {
    // Nothing to do
  }

  @Override
  public void hideLoading() {
    // Nothing to do
  }

  @Override
  public void showRetry() {
    // Nothing to do
  }

  @Override
  public void hideRetry() {
    // Nothing to do
  }

  @Override
  public void showError(String message) {
    showToastMessage(message);
  }

  private void loadTweets() {
    tweetsPresenter.loadTweets();
  }

  @Override
  public void showTweets() {
    // TODO
  }

  @Override
  public Context context() {
    return getActivity();
  }
}
