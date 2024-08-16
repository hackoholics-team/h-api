package app.hackoholics.api.service.google.maps;

import com.google.maps.GeoApiContext;
import com.google.maps.PlacesApi;
import com.google.maps.errors.ApiException;
import com.google.maps.model.PlacesSearchResult;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class MapsApi {
  private final GeoApiContext geoApiContext;

  public MapsApi(@Value("${google.api.key}") String apiKey) {
    this.geoApiContext = new GeoApiContext.Builder()
        .apiKey(apiKey)
        .build();
  }

  public PlacesSearchResult[] getNationalParkAndNatureReserve(String address) throws IOException, InterruptedException, ApiException {
    String QUERY_TEMPLATE = "National parks and nature reserve around %s";
    return PlacesApi
        .textSearchQuery(geoApiContext, String.format(QUERY_TEMPLATE, address))
        .await()
        .results;
  }

}
