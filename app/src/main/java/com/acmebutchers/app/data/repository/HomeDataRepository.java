package com.acmebutchers.app.data.repository;

import com.acmebutchers.app.data.entity.response.PhotoId;
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

  private Observable<Photos> searchPhotos(String text, String[] tags) {
    return apiService.searchPhotos("flickr.photos.search", APIService.FLICKR_API_KEY, text, tags);
  }

  @Override
  public Observable<List<String>> getImageUrls(String text, String[] tags) {
    return searchPhotos(text, tags).map(new Func1<Photos, List<String>>() {

      @Override
      public List<String> call(Photos photos) {
        List<String> imageUrls = new ArrayList<>();

        if (photos != null) {
          for (PhotoId photoId : photos.photos()) {
            String id = photoId.id();
            String owner = photoId.owner();
            String url = "https://www.flickr.com/photos/" + owner + "/" + id;
            imageUrls.add(url);
          }
        }

        return imageUrls;
      }
    });
  }
}
