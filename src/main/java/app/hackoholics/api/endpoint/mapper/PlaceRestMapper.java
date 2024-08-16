package app.hackoholics.api.endpoint.mapper;

import app.hackoholics.api.endpoint.rest.model.PlaceDetails;
import app.hackoholics.api.endpoint.rest.model.PlacesSearchResult;
import app.hackoholics.api.endpoint.rest.model.PlacesSearchResultGeometry;
import app.hackoholics.api.endpoint.rest.model.PlacesSearchResultOpeningHours;
import app.hackoholics.api.endpoint.security.AuthProvider;
import app.hackoholics.api.service.RequirementService;
import app.hackoholics.api.service.google.gemini.GeminiService;
import java.util.Arrays;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class PlaceRestMapper {
  public static final String PHOTO_BASE_URL =
      "https://maps.googleapis.com/maps/api/place/photo?maxwidth=400&photoreference=%s&key=";
  private final GeminiService geminiService;
  private final RequirementService requirementService;

  public PlacesSearchResult toRest(com.google.maps.model.PlacesSearchResult model) {
    var geometry = model.geometry;
    var bounds = geometry.viewport;
    var openings = model.openingHours;
    var photos = model.photos;
    return new PlacesSearchResult()
        .placeId(model.placeId)
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

  public PlaceDetails toRest(com.google.maps.model.PlaceDetails model) {
    var auth = AuthProvider.getUser();
    var requirements = String.join(", ", requirementService.getRequirements(auth.getId()));
    var reason = geminiService.describe(model.name, requirements);
    var geometry = model.geometry;
    var bounds = geometry.viewport;
    var photos =
        model.photos == null
            ? null
            : Arrays.stream(model.photos)
                .map(photo -> String.format(PHOTO_BASE_URL, photo.photoReference))
                .toList();
    var photo = photos == null ? null : photos.getFirst();
    return new PlaceDetails()
        .placeId(model.placeId)
        .name(model.name)
        .address(model.formattedAddress)
        .overview(model.editorialSummary.overview)
        .localPhone(model.formattedPhoneNumber)
        .internationalPhone(model.internationalPhoneNumber)
        .website(model.website == null ? null : model.website.toString())
        .reason(reason)
        .photos(photos)
        .photo(photo)
        .rating((double) model.rating)
        .totalRate(model.userRatingsTotal)
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
