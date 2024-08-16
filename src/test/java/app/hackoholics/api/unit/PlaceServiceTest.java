package app.hackoholics.api.unit;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import app.hackoholics.api.service.PlaceService;
import app.hackoholics.api.service.google.maps.MapsApi;
import com.google.maps.errors.ApiException;
import com.google.maps.model.PlaceDetails;
import com.google.maps.model.PlacesSearchResult;
import java.io.IOException;
import java.util.Arrays;
import org.junit.jupiter.api.Test;

class PlaceServiceTest {
  private final MapsApi mapsApi = mock();
  public final PlaceService subject = new PlaceService(mapsApi);

  @Test
  void read_places_ok() throws IOException, InterruptedException, ApiException {
    when(mapsApi.getNationalParkAndNatureReserve(any())).thenReturn(new PlacesSearchResult[] {});
    var actual = subject.getNationalParkAndNatureReserve("dummy");

    assertTrue(Arrays.stream(actual).toList().isEmpty());
  }

  @Test
  void read_place_details_ok() throws IOException, InterruptedException, ApiException {
    when(mapsApi.getPlaceDetails(any())).thenReturn(new PlaceDetails());
    var actual = subject.getPlaceDetails("dummy");

    assertNotNull(actual);
  }
}
