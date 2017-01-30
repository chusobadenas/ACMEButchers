package com.acmebutchers.app.data.entity.response;

import com.acmebutchers.app.data.entity.LocationEntity;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;

public class PlaceSearchTest {

  @Test
  public void testCreationSuccess() {
    LocationEntity locationEntity = LocationEntity.create(0.0, 1.0);
    PlaceGeometry placeGeometry = PlaceGeometry.create(locationEntity);

    LocationEntity locationEntity2 = LocationEntity.create(1.0, 0.0);
    PlaceGeometry placeGeometry2 = PlaceGeometry.create(locationEntity2);

    PlaceId placeOne = PlaceId.create("1", "Place one", placeGeometry);
    PlaceId placeTwo = PlaceId.create("2", "Place two", placeGeometry2);

    List<PlaceId> places = Arrays.asList(placeOne, placeTwo);
    PlaceSearch placeSearch = PlaceSearch.create(places);

    assertNotNull(placeSearch);
    assertSame(placeSearch.results().size(), 2);
    assertEquals(placeSearch.results().get(0).id(), "1");
    assertEquals(placeSearch.results().get(0).name(), "Place one");
    assertEquals(placeSearch.results().get(0).geometry().location(), locationEntity);
    assertEquals(placeSearch.results().get(1).id(), "2");
    assertEquals(placeSearch.results().get(1).name(), "Place two");
    assertEquals(placeSearch.results().get(1).geometry().location(), locationEntity2);
  }
}
