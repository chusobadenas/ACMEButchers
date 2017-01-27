package com.acmebutchers.app.data.entity.response;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.SerializedName;

@AutoValue
public abstract class PlaceId {

  @SerializedName("id")
  public abstract String id();

  @SerializedName("name")
  public abstract String name();

  @SerializedName("geometry")
  public abstract PlaceGeometry geometry();

  public static PlaceId create(String id, String name, PlaceGeometry geometry) {
    return new AutoValue_PlaceId(id, name, geometry);
  }

  public static TypeAdapter<PlaceId> typeAdapter(Gson gson) {
    return new AutoValue_PlaceId.GsonTypeAdapter(gson);
  }
}
