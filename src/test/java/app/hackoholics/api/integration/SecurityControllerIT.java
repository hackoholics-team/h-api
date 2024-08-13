package app.hackoholics.api.integration;

import static app.hackoholics.api.conf.TestConf.BAD_TOKEN;
import static app.hackoholics.api.conf.TestConf.PROCESSING_TOKEN;
import static app.hackoholics.api.conf.TestConf.VALID_TOKEN;
import static app.hackoholics.api.conf.TestConf.anAvailablePort;
import static app.hackoholics.api.conf.TestConf.setUpFirebaseService;
import static app.hackoholics.api.conf.TestConf.user;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

import app.hackoholics.api.AbstractContextInitializer;
import app.hackoholics.api.conf.TestConf;
import app.hackoholics.api.endpoint.rest.api.SecurityApi;
import app.hackoholics.api.endpoint.rest.client.ApiClient;
import app.hackoholics.api.endpoint.rest.client.ApiException;
import app.hackoholics.api.service.google.GoogleConf;
import app.hackoholics.api.service.google.firebase.FirebaseService;
import app.hackoholics.api.service.google.gemini.GeminiService;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@Testcontainers
@ContextConfiguration(initializers = SecurityControllerIT.ContextInitializer.class)
@AutoConfigureMockMvc
class SecurityControllerIT {
  @MockBean GoogleConf googleConf;
  @MockBean GeminiService geminiService;
  @MockBean FirebaseService firebaseService;

  private ApiClient anApiClient(String token) {
    return TestConf.anApiClient(token, ContextInitializer.SERVER_PORT);
  }

  @BeforeEach
  void setUp() {
    setUpFirebaseService(firebaseService);
  }

  @Test
  void authenticate_user_ok() throws ApiException {
    ApiClient client = anApiClient(VALID_TOKEN);
    SecurityApi api = new SecurityApi(client);

    var actual = api.signIn();

    assertEquals(user(), actual);
  }

  @Test
  void authenticate_user_ko() throws IOException, InterruptedException {
    HttpClient unauthenticatedClient = HttpClient.newBuilder().build();
    String basePath = "http://localhost:" + SecurityControllerIT.ContextInitializer.SERVER_PORT;

    HttpResponse<String> response =
        unauthenticatedClient.send(
            HttpRequest.newBuilder()
                .uri(URI.create(basePath + "/signin"))
                .header("Access-Control-Request-Method", "POST")
                .header("Authorization", "Bearer " + BAD_TOKEN)
                .build(),
            HttpResponse.BodyHandlers.ofString());

    assertEquals(HttpStatus.UNAUTHORIZED.value(), response.statusCode());
  }

  // TODO: Should return processing status code 102
  @Test
  void authenticate_processing_user() throws IOException, InterruptedException {
    HttpClient unauthenticatedClient = HttpClient.newBuilder().build();
    String basePath = "http://localhost:" + SecurityControllerIT.ContextInitializer.SERVER_PORT;

    HttpResponse<String> response =
        unauthenticatedClient.send(
            HttpRequest.newBuilder()
                .uri(URI.create(basePath + "/signin"))
                .header("Access-Control-Request-Method", "POST")
                .header("Authorization", "Bearer " + PROCESSING_TOKEN)
                .build(),
            HttpResponse.BodyHandlers.ofString());

    assertEquals(HttpStatus.UNAUTHORIZED.value(), response.statusCode());
  }

  @Test
  void create_user_ok() throws ApiException {
    ApiClient client = anApiClient(VALID_TOKEN);
    SecurityApi api = new SecurityApi(client);

    var actual = api.signUp(user());

    assertEquals(user(), actual);
  }

  static class ContextInitializer extends AbstractContextInitializer {
    public static final int SERVER_PORT = anAvailablePort();

    @Override
    public int getServerPort() {
      return SERVER_PORT;
    }
  }
}
