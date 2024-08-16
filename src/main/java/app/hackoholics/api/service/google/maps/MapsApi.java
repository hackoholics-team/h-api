package app.hackoholics.api.service.google.maps;

import com.google.maps.GeoApiContext;
import com.google.maps.PlacesApi;
import com.google.maps.errors.ApiException;
import com.google.maps.model.PlaceDetails;
import com.google.maps.model.PlacesSearchResult;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class MapsApi {
  private final GeoApiContext geoApiContext;

  public MapsApi(@Value("${google.api.key}") String apiKey) {
    this.geoApiContext = new GeoApiContext.Builder().apiKey(apiKey).build();
  }

  public PlacesSearchResult[] getNationalParkAndNatureReserve(String address)
      throws IOException, InterruptedException, ApiException {
    String queryTemplate = "National parks and nature reserve around %s";
    return PlacesApi.textSearchQuery(geoApiContext, String.format(queryTemplate, address))
        .await()
        .results;
  }

  public PlaceDetails getPlaceDetails(String placeId)
      throws IOException, InterruptedException, ApiException {
    return PlacesApi.placeDetails(geoApiContext, placeId).await();
  }
}
