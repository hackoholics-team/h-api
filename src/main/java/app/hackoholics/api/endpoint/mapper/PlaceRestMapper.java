package app.hackoholics.api.endpoint.mapper;

import app.hackoholics.api.endpoint.rest.model.PlacesSearchResult;
import app.hackoholics.api.endpoint.rest.model.PlacesSearchResultGeometry;
import app.hackoholics.api.endpoint.rest.model.PlacesSearchResultOpeningHours;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class PlaceRestMapper {

  public PlacesSearchResult toRest(com.google.maps.model.PlacesSearchResult model) {
    var geometry = model.geometry;
    var bounds = geometry.viewport;
    return new PlacesSearchResult()
        .name(model.name)
        .address(model.formattedAddress)
        .photo(model.photos[0].photoReference)
        .rating((double) model.rating)
        .totalRate(model.userRatingsTotal)
        .openingHours(
            new PlacesSearchResultOpeningHours()
                .open(model.openingHours.periods[0].open.time.toString())
                .close(model.openingHours.periods[0].close.time.toString()))
        .geometry(
            new PlacesSearchResultGeometry()
                .lat(geometry.location.lat)
                .lon(geometry.location.lng)
                .bounds(
                    List.of(
                        bounds.northeast.lat,
                        bounds.northeast.lng,
                        bounds.southwest.lat,
                        bounds.southwest.lng)));
  }
}
