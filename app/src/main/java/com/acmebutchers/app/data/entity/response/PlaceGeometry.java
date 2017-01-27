package com.acmebutchers.app.data.entity.response;

import com.acmebutchers.app.data.entity.LocationEntity;
import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.SerializedName;

@AutoValue
public abstract class PlaceGeometry {

  @SerializedName("location")
  public abstract LocationEntity location();

  public static PlaceGeometry create(LocationEntity location) {
    return new AutoValue_PlaceGeometry(location);
  }

  public static TypeAdapter<PlaceGeometry> typeAdapter(Gson gson) {
    return new AutoValue_PlaceGeometry.GsonTypeAdapter(gson);
  }
}
