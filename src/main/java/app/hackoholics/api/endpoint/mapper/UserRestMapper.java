package app.hackoholics.api.endpoint.mapper;

import app.hackoholics.api.endpoint.rest.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserRestMapper {
  public User toRest(app.hackoholics.api.model.User domain) {
    return new User()
        .id(domain.getId())
        .firstName(domain.getFirstName())
        .lastName(domain.getLastName())
        .birthDate(domain.getBirthDate())
        .email(domain.getEmail())
        .username(domain.getUsername())
        .firebaseId(domain.getFirebaseId())
        .photoId(domain.getPhotoId());
  }

  public app.hackoholics.api.model.User toDomain(User rest) {
    return app.hackoholics.api.model.User.builder()
        .id(rest.getId())
        .firstName(rest.getFirstName())
        .lastName(rest.getLastName())
        .birthDate(rest.getBirthDate())
        .email(rest.getEmail())
        .username(rest.getUsername())
        .firebaseId(rest.getFirebaseId())
        .photoId(rest.getPhotoId())
        .build();
  }
}
