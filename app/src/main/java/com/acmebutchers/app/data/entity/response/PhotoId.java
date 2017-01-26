package com.acmebutchers.app.data.entity.response;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.SerializedName;

@AutoValue
public abstract class PhotoId {

  @SerializedName("id")
  public abstract String id();

  @SerializedName("owner")
  public abstract String owner();

  @SerializedName("secret")
  public abstract String secret();

  @SerializedName("server")
  public abstract String server();

  @SerializedName("farm")
  public abstract Integer farm();

  public static PhotoId create(String id, String owner, String secret, String server, Integer
      farm) {
    return new AutoValue_PhotoId(id, owner, secret, server, farm);
  }

  public static TypeAdapter<PhotoId> typeAdapter(Gson gson) {
    return new AutoValue_PhotoId.GsonTypeAdapter(gson);
  }
}
