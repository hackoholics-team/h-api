package app.hackoholics.api.endpoint.controller;

import app.hackoholics.api.endpoint.mapper.PlaceRestMapper;
import app.hackoholics.api.endpoint.rest.model.PlaceDetails;
import app.hackoholics.api.endpoint.rest.model.PlacesSearchResult;
import app.hackoholics.api.service.PlaceService;
import java.util.Arrays;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class PlaceController {
  private final PlaceService service;
  private final PlaceRestMapper mapper;

  @GetMapping("/places")
  public List<PlacesSearchResult> getParkAndNReserve(@RequestParam("location") String location) {
    return Arrays.stream(service.getNationalParkAndNatureReserve(location))
        .map(mapper::toRest)
        .toList();
  }

  @GetMapping("/places/about")
  public PlaceDetails getPlaceDetails(@RequestParam("placeId") String placeId) {
    return mapper.toRest(service.getPlaceDetails(placeId));
  }
}
