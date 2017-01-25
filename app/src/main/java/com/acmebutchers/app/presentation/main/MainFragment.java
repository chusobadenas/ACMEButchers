package com.acmebutchers.app.presentation.main;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.acmebutchers.app.R;
import com.acmebutchers.app.common.di.components.MainComponent;
import com.acmebutchers.app.presentation.base.BaseFragment;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Main fragment
 */
public class MainFragment extends BaseFragment implements MainMvpView {

  @Inject
  MainPresenter mainPresenter;

  @BindView(R.id.home_info_view)
  LinearLayout homeInfoView;
  @BindView(R.id.rl_progress)
  RelativeLayout progressView;
  @BindView(R.id.rl_retry)
  RelativeLayout retryView;

  private Unbinder unbinder;

  /**
   * Creates a new instance of a MainFragment.
   */
  public static MainFragment newInstance() {
    return new MainFragment();
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    getComponent(MainComponent.class).inject(this);
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    View fragmentView = inflater.inflate(R.layout.fragment_main, container, false);
    unbinder = ButterKnife.bind(this, fragmentView);
    mainPresenter.attachView(this);
    setupView();
    return fragmentView;
  }

  @Override
  public void onStart() {
    super.onStart();
    loadImages();
  }

  @Override
  public void onDestroyView() {
    super.onDestroyView();
    unbinder.unbind();
  }

  @Override
  public void onDestroy() {
    super.onDestroy();
    mainPresenter.detachView();
  }

  @Override
  public void showLoading() {
    homeInfoView.setVisibility(View.GONE);
    progressView.setVisibility(View.VISIBLE);
  }

  @Override
  public void hideLoading() {
    progressView.setVisibility(View.GONE);
    homeInfoView.setVisibility(View.VISIBLE);
  }

  @Override
  public void showRetry() {
    homeInfoView.setVisibility(View.GONE);
    retryView.setVisibility(View.VISIBLE);
  }

  @Override
  public void hideRetry() {
    retryView.setVisibility(View.GONE);
    homeInfoView.setVisibility(View.VISIBLE);
  }

  @Override
  public void showError(String message) {
    showToastMessage(message);
  }

  @OnClick(R.id.bt_retry)
  void onButtonRetryClick() {
    // TODO
  }

  /**
   * Loads home information
   */
  private void setupView() {
    // TODO
  }

  /**
   * Loads home images
   */
  private void loadImages() {
    mainPresenter.initialize();
  }

  @Override
  public void displayHomeImages(List<String> imageUrls) {
    // TODO
  }

  @Override
  public Context context() {
    return getActivity();
  }
}
