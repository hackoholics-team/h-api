package app.hackoholics.api.endpoint.mapper;

import app.hackoholics.api.endpoint.rest.model.PreferedPlace;
import app.hackoholics.api.endpoint.security.AuthProvider;
import app.hackoholics.api.model.PreferredPlace;
import org.springframework.stereotype.Component;

@Component
public class PreferredPlaceRestMapper {
  public PreferedPlace toRest(PreferredPlace domain) {
    return new PreferedPlace().placeId(domain.getId());
  }

  public PreferredPlace toDomain(PreferedPlace domain) {
    return PreferredPlace.builder()
        .id(domain.getPlaceId())
        .userId(AuthProvider.getUser().getId())
        .build();
  }
}
