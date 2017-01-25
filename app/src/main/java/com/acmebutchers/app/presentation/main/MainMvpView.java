package com.acmebutchers.app.presentation.main;

import com.acmebutchers.app.presentation.base.MvpView;

import java.util.List;

/**
 * Interface representing a View in a model view presenter (MVP) pattern.
 */
interface MainMvpView extends MvpView {

  /**
   * Displays the images of the home view.
   *
   * @param imageUrls the list of image urls to display
   */
  void displayHomeImages(List<String> imageUrls);
}
