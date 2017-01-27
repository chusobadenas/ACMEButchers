package com.acmebutchers.app.data.entity.response;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.SerializedName;

import java.util.List;

@AutoValue
public abstract class PlaceSearch {

  @SerializedName("results")
  public abstract List<PlaceId> results();

  public static PlaceSearch create(List<PlaceId> results) {
    return new AutoValue_PlaceSearch(results);
  }

  public static TypeAdapter<PlaceSearch> typeAdapter(Gson gson) {
    return new AutoValue_PlaceSearch.GsonTypeAdapter(gson);
  }
}
