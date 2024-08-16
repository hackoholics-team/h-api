package app.hackoholics.api.endpoint.mapper;

import app.hackoholics.api.endpoint.rest.model.PlacesSearchResult;
import app.hackoholics.api.endpoint.rest.model.PlacesSearchResultGeometry;
import app.hackoholics.api.endpoint.rest.model.PlacesSearchResultOpeningHours;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class PlaceRestMapper {
  public static final String PHOTO_BASE_URL =
      "https://maps.googleapis.com/maps/api/place/photo?maxwidth=400&photoreference=%s&key=";

  public PlacesSearchResult toRest(com.google.maps.model.PlacesSearchResult model) {
    var geometry = model.geometry;
    var bounds = geometry.viewport;
    var openings = model.openingHours;
    var photos = model.photos;
    return new PlacesSearchResult()
        .name(model.name)
        .address(model.formattedAddress)
        .photo(photos == null ? null : String.format(PHOTO_BASE_URL, photos[0].photoReference))
        .rating((double) model.rating)
        .totalRate(model.userRatingsTotal)
        .openingHours(
            new PlacesSearchResultOpeningHours()
                .open(
                    openings == null || openings.periods == null
                        ? null
                        : openings.periods[0].open.time.toString())
                .close(
                    openings == null || openings.periods == null
                        ? null
                        : openings.periods[0].close.time.toString()))
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
