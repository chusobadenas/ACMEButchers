package com.acmebutchers.app.domain;

import com.acmebutchers.app.data.entity.LocationEntity;
import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;

@AutoValue
public abstract class Place {

  public abstract String name();

  public abstract LocationEntity location();

  public static Place create(String name, LocationEntity location) {
    return new AutoValue_Place(name, location);
  }

  public static TypeAdapter<Place> typeAdapter(Gson gson) {
    return new AutoValue_Place.GsonTypeAdapter(gson);
  }
}
