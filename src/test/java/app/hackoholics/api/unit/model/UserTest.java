package app.hackoholics.api.unit.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import app.hackoholics.api.endpoint.security.Principal;
import app.hackoholics.api.model.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

public class UserTest {
  public final ObjectMapper mapper = new ObjectMapper();

  User user() {
    return User.builder()
        .id("dummy")
        .username("dummy")
        .firstName("dummy")
        .lastName("dummy")
        .photoId("dummy")
        .email("dummy")
        .firebaseId("dummy")
        .build();
  }

  @Test
  void serialize_user() throws JsonProcessingException {
    var serialized = mapper.writeValueAsString(user());
    var deserialized = mapper.readValue(serialized, User.class);

    assertEquals(user(), deserialized);
  }

  @Test
  void serialize_principal() throws JsonProcessingException {
    var principal = new Principal("bearer", user());

    var serialized = mapper.writeValueAsString(principal);
    var deserialized = mapper.readValue(serialized, Principal.class);

    assertEquals(principal, deserialized);
    assertEquals("bearer", deserialized.getPassword());
    assertEquals(user(), deserialized.getUser());
    assertTrue(deserialized.getAuthorities().isEmpty());
    assertTrue(deserialized.isAccountNonExpired());
    assertTrue(deserialized.isAccountNonLocked());
    assertTrue(deserialized.isEnabled());
    assertTrue(deserialized.isCredentialsNonExpired());
  }
}
