package app.hackoholics.api.integration;

import static app.hackoholics.api.conf.TestConf.VALID_TOKEN;
import static app.hackoholics.api.conf.TestConf.anAvailablePort;
import static app.hackoholics.api.conf.TestConf.setUpFirebaseService;
import static app.hackoholics.api.conf.TestConf.user;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

import app.hackoholics.api.AbstractContextInitializer;
import app.hackoholics.api.conf.TestConf;
import app.hackoholics.api.endpoint.rest.api.UserApi;
import app.hackoholics.api.endpoint.rest.client.ApiClient;
import app.hackoholics.api.endpoint.rest.client.ApiException;
import app.hackoholics.api.endpoint.rest.model.PreferedPlace;
import app.hackoholics.api.service.FileService;
import app.hackoholics.api.service.google.GoogleConf;
import app.hackoholics.api.service.google.firebase.FirebaseService;
import app.hackoholics.api.service.google.gemini.GeminiService;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@Testcontainers
@ContextConfiguration(initializers = PreferredPlaceControllerIT.ContextInitializer.class)
@AutoConfigureMockMvc
public class PreferredPlaceControllerIT {
  @MockBean GoogleConf googleConf;
  @MockBean GeminiService geminiService;
  @MockBean FileService fileService;
  @MockBean FirebaseService firebaseService;

  private ApiClient anApiClient(String token) {
    return TestConf.anApiClient(token, PreferredPlaceControllerIT.ContextInitializer.SERVER_PORT);
  }

  PreferedPlace place() {
    return new PreferedPlace().placeId("dummy");
  }

  @Test
  void get_and_crupdate_preferences_ok() throws ApiException {
    ApiClient client = anApiClient(VALID_TOKEN);
    UserApi api = new UserApi(client);

    var actual = api.crupdateUserPreferences(user().getId(), List.of(place()));
    var persisted = api.getPreferences(user().getId());

    assertTrue(actual.containsAll(persisted));
  }

  @BeforeEach
  void setUp() {
    setUpFirebaseService(firebaseService);
  }

  static class ContextInitializer extends AbstractContextInitializer {
    public static final int SERVER_PORT = anAvailablePort();

    @Override
    public int getServerPort() {
      return SERVER_PORT;
    }
  }
}
