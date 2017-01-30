package com.acmebutchers.app.presentation.main;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

import com.acmebutchers.app.R;
import com.acmebutchers.app.common.di.components.MainComponent;
import com.acmebutchers.app.presentation.base.BaseFragment;
import com.bumptech.glide.Glide;

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

  private static final int TOTAL_NUM_IMAGES = 4;

  @Inject
  MainPresenter mainPresenter;

  @BindView(R.id.content_main)
  ScrollView homeInfoView;
  @BindView(R.id.main_quality_image)
  ImageView qualityImageView;
  @BindView(R.id.main_service_image)
  ImageView serviceImageView;
  @BindView(R.id.main_restoration_image)
  ImageView restorationImageView;
  @BindView(R.id.main_butcher_image)
  ImageView butcherImageView;
  @BindView(R.id.rl_progress)
  RelativeLayout progressView;
  @BindView(R.id.rl_retry)
  RelativeLayout retryView;

  private Unbinder unbinder;

  /**
   * Creates a new instance of {@link MainFragment}.
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
    hideRetry();
    loadImages();
  }

  /**
   * Loads home images
   */
  private void loadImages() {
    mainPresenter.initialize();
  }

  /**
   * Used to load images in a view with Glide
   *
   * @param view the image view
   * @param url  the url of the image
   */
  private void loadImage(ImageView view, String url) {
    Glide.with(this)
        .load(url)
        .centerCrop()
        .placeholder(R.color.bg_light_grey)
        .crossFade()
        .into(view);
  }

  @Override
  public void displayHomeImages(List<String> imageUrls) {
    if (imageUrls == null || imageUrls.isEmpty() || imageUrls.size() < TOTAL_NUM_IMAGES) {
      showRetry();
    } else {
      hideRetry();
      // Load images
      loadImage(qualityImageView, imageUrls.get(0));
      loadImage(serviceImageView, imageUrls.get(1));
      loadImage(restorationImageView, imageUrls.get(2));
      loadImage(butcherImageView, imageUrls.get(3));
    }
  }

  @Override
  public Context context() {
    return getActivity();
  }
}
