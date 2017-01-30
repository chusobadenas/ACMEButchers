package com.acmebutchers.app.data.repository;

import com.acmebutchers.app.BuildConfig;
import com.acmebutchers.app.data.entity.response.PhotoId;
import com.acmebutchers.app.data.entity.response.PhotoSearch;
import com.acmebutchers.app.data.entity.response.Photos;
import com.acmebutchers.app.data.repository.remote.FlickrApiService;
import com.acmebutchers.app.domain.repository.HomeRepository;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.functions.Func1;

public class HomeDataRepository implements HomeRepository {

  private final FlickrApiService flickrApiService;

  @Inject
  public HomeDataRepository(FlickrApiService flickrApiService) {
    this.flickrApiService = flickrApiService;
  }

  private Observable<PhotoSearch> searchPhotos(String text, String[] tags) {
    return flickrApiService.searchPhotos(FlickrApiService.FLICKR_SEARCH_METHOD,
        BuildConfig.FLICKR_API_KEY, FlickrApiService.JSON_FORMAT,
        FlickrApiService.NO_JSON_CALLBACK, text, tags);
  }

  @Override
  public Observable<List<String>> getImageUrls(String text, String[] tags) {
    return searchPhotos(text, tags).map(new Func1<PhotoSearch, List<String>>() {

      @Override
      public List<String> call(PhotoSearch photoSearch) {
        List<String> imageUrls = new ArrayList<>();

        if (photoSearch != null) {
          Photos photos = photoSearch.result();
          for (PhotoId photoId : photos.photos()) {
            String url = "https://farm" + photoId.farm() + ".staticflickr.com/" + photoId.server
                () + '/' + photoId.id() + '_' + photoId.secret() + ".jpg";
            imageUrls.add(url);
          }
        }

        return imageUrls;
      }
    });
  }
}
