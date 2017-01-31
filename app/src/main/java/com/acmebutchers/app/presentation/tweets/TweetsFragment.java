package com.acmebutchers.app.presentation.tweets;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.acmebutchers.app.R;
import com.acmebutchers.app.common.util.UIUtils;
import com.acmebutchers.app.presentation.main.MainActivity;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import twitter4j.Status;

public class TweetsFragment extends ListFragment implements TweetsMvpView {

  @Inject
  TweetsPresenter tweetsPresenter;

  @BindView(R.id.content_tweets)
  LinearLayout tweetsView;
  @BindView(R.id.rl_progress)
  RelativeLayout progressView;

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
    ((MainActivity) getActivity()).getComponent().inject(this);
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
    tweetsView.setVisibility(View.GONE);
    progressView.setVisibility(View.VISIBLE);
  }

  @Override
  public void hideLoading() {
    progressView.setVisibility(View.GONE);
    tweetsView.setVisibility(View.VISIBLE);
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
    UIUtils.showToastMessage(context(), message);
  }

  private void loadTweets() {
    tweetsPresenter.loadTweets();
  }

  @Override
  public void showTweets(List<Status> tweets) {
    // Set tweets to list
    TweetsAdapter adapter = new TweetsAdapter(context(), tweets);
    setListAdapter(adapter);
  }

  @Override
  public Context context() {
    return getActivity();
  }
}
