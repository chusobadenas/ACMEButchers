package com.acmebutchers.app.data.entity.response;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.SerializedName;

import java.util.List;

@AutoValue
public abstract class Photos {

  @SerializedName("page")
  public abstract Integer page();

  @SerializedName("pages")
  public abstract Integer pages();

  @SerializedName("perpage")
  public abstract Integer perPage();

  @SerializedName("total")
  public abstract String total();

  @SerializedName("photo")
  public abstract List<PhotoId> photos();

  public static Photos create(Integer page, Integer pages, Integer perPage, String total,
                              List<PhotoId> photos) {
    return new AutoValue_Photos(page, pages, perPage, total, photos);
  }

  public static TypeAdapter<Photos> typeAdapter(Gson gson) {
    return new AutoValue_Photos.GsonTypeAdapter(gson);
  }
}
