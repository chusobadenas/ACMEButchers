package com.acmebutchers.app.data.repository;

import com.acmebutchers.app.data.entity.response.PhotoId;
import com.acmebutchers.app.data.entity.response.PhotoSearch;
import com.acmebutchers.app.data.entity.response.Photos;
import com.acmebutchers.app.data.repository.remote.APIService;
import com.acmebutchers.app.domain.repository.HomeRepository;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.functions.Func1;

public class HomeDataRepository implements HomeRepository {

  private final APIService apiService;

  @Inject
  public HomeDataRepository(APIService apiService) {
    this.apiService = apiService;
  }

  private Observable<PhotoSearch> searchPhotos(String text, String[] tags) {
    return apiService.searchPhotos(APIService.FLICKER_SEARCH_METHOD, APIService.FLICKR_API_KEY,
        APIService.JSON_FORMAT, APIService.NO_JSON_CALLBACK, text, tags);
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
