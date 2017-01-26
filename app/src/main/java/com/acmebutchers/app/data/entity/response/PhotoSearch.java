package com.acmebutchers.app.data.entity.response;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.SerializedName;

@AutoValue
public abstract class PhotoSearch {

  @SerializedName("photos")
  public abstract Photos result();

  public static PhotoSearch create(Photos result) {
    return new AutoValue_PhotoSearch(result);
  }

  public static TypeAdapter<PhotoSearch> typeAdapter(Gson gson) {
    return new AutoValue_PhotoSearch.GsonTypeAdapter(gson);
  }
}
