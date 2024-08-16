package app.hackoholics.api.service;

import app.hackoholics.api.service.google.maps.MapsApi;
import com.google.maps.model.PlacesSearchResult;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PlaceService {
  private final MapsApi mapsApi;

  @SneakyThrows
  public PlacesSearchResult[] getNationalParkAndNatureReserve(String address) {
    return mapsApi.getNationalParkAndNatureReserve(address);
  }
}
