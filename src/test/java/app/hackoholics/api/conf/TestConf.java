package app.hackoholics.api.conf;

import static org.mockito.Mockito.when;

import app.hackoholics.api.endpoint.rest.client.ApiClient;
import app.hackoholics.api.endpoint.rest.model.User;
import app.hackoholics.api.service.google.firebase.FirebaseService;
import app.hackoholics.api.service.google.firebase.FirebaseUser;
import java.io.IOException;
import java.net.ServerSocket;
import java.time.Instant;

public class TestConf {
  public static final String VALID_TOKEN = "valid_token";

  public static ApiClient anApiClient(String token, int serverPort) {
    ApiClient client = new ApiClient();
    client.setScheme("http");
    client.setHost("localhost");
    client.setPort(serverPort);
    client.setRequestInterceptor(
        httpRequestBuilder -> httpRequestBuilder.header("Authorization", "Bearer " + token));
    return client;
  }

  public static int anAvailablePort() {
    try {
      return new ServerSocket(0).getLocalPort();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public static void setUpFirebaseService(FirebaseService firebaseServiceMock) {
    when(firebaseServiceMock.getUserByBearer(VALID_TOKEN))
        .thenReturn(new FirebaseUser(authUser().getEmail(), authUser().getFirebaseId()));
  }

  public static app.hackoholics.api.model.User authUser() {
    return app.hackoholics.api.model.User.builder()
        .id("user1_id")
        .firstName("John")
        .lastName("Doe")
        .email("john@email.com")
        .birthDate(Instant.parse("2002-01-01T01:00:00.00Z"))
        .creationDatetime(Instant.parse("2022-01-01T01:00:00.00Z"))
        .photoId("user1_photo_id")
        .username(null)
        .firebaseId("user1_firebase_id")
        .build();
  }

  public static User user() {
    return new User()
        .id("user1_id")
        .firstName("John")
        .lastName("Doe")
        .email("john@email.com")
        .birthDate(Instant.parse("2002-01-01T01:00:00.00Z"))
        .photoId("user1_photo_id")
        .username(null)
        .firebaseId("user1_firebase_id");
  }
}
