package com.acmebutchers.app.data.entity;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.SerializedName;

@AutoValue
public abstract class LocationEntity {

  @SerializedName("lat")
  public abstract Double latitude();

  @SerializedName("lng")
  public abstract Double longitude();

  public static LocationEntity create(Double latitude, Double longitude) {
    return new AutoValue_LocationEntity(latitude, longitude);
  }

  public static TypeAdapter<LocationEntity> typeAdapter(Gson gson) {
    return new AutoValue_LocationEntity.GsonTypeAdapter(gson);
  }
}
