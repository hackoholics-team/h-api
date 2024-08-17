package app.hackoholics.api.integration;

import static app.hackoholics.api.conf.TestConf.VALID_TOKEN;
import static app.hackoholics.api.conf.TestConf.anAvailablePort;
import static app.hackoholics.api.conf.TestConf.setUpFirebaseService;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

import app.hackoholics.api.AbstractContextInitializer;
import app.hackoholics.api.service.FileService;
import app.hackoholics.api.service.google.GoogleConf;
import app.hackoholics.api.service.google.firebase.FirebaseService;
import app.hackoholics.api.service.google.gemini.GeminiService;
import app.hackoholics.api.service.stripe.StripeConf;
import com.stripe.model.Customer;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Arrays;
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
@ContextConfiguration(initializers = FileControllerIT.ContextInitializer.class)
@AutoConfigureMockMvc
class FileControllerIT {
  private static final String FILE_ID = "fileId";
  private static final byte[] file = "photo.png".getBytes();
  @MockBean GoogleConf googleConf;
  @MockBean GeminiService geminiService;
  @MockBean FileService fileService;
  @MockBean FirebaseService firebaseService;
  @MockBean StripeConf stripeConf;

  @BeforeEach
  void setUp() {
    setUpFirebaseService(firebaseService);
  }

  @Test
  void download_file_ok() throws IOException, InterruptedException {
    when(fileService.downloadFile(any())).thenReturn(file);
    HttpClient unauthenticatedClient = HttpClient.newBuilder().build();
    String basePath = "http://localhost:" + FileControllerIT.ContextInitializer.SERVER_PORT;

    HttpResponse<byte[]> response =
        unauthenticatedClient.send(
            HttpRequest.newBuilder()
                .uri(URI.create(basePath + "/files/" + FILE_ID + "/raw"))
                .GET()
                .header("Authorization", "Bearer " + VALID_TOKEN)
                .build(),
            HttpResponse.BodyHandlers.ofByteArray());

    assertEquals(HttpStatus.OK.value(), response.statusCode());
    assertEquals(Arrays.toString(file), Arrays.toString(response.body()));
  }

  @Test
  void upload_file_ok() throws IOException, InterruptedException {
    when(fileService.uploadFile(any(), any())).thenReturn(FILE_ID);
    HttpClient unauthenticatedClient = HttpClient.newBuilder().build();
    String basePath = "http://localhost:" + FileControllerIT.ContextInitializer.SERVER_PORT;

    HttpResponse<String> response =
        unauthenticatedClient.send(
            HttpRequest.newBuilder()
                .uri(URI.create(basePath + "/files/" + FILE_ID + "/raw"))
                .header("Authorization", "Bearer " + VALID_TOKEN)
                .method("PUT", HttpRequest.BodyPublishers.ofByteArray(file))
                .build(),
            HttpResponse.BodyHandlers.ofString());

    assertEquals(HttpStatus.OK.value(), response.statusCode());
    assertEquals(FILE_ID, response.body());
  }

  @Test
  void upload_user_file_ok() throws IOException, InterruptedException {
    when(fileService.uploadFile(any(), any())).thenReturn(FILE_ID);
    var customer = new Customer();
    customer.setId("dummy");
    when(stripeConf.createCustomer(any())).thenReturn(customer);

    HttpClient unauthenticatedClient = HttpClient.newBuilder().build();
    String basePath = "http://localhost:" + FileControllerIT.ContextInitializer.SERVER_PORT;

    HttpResponse<String> response =
        unauthenticatedClient.send(
            HttpRequest.newBuilder()
                .uri(URI.create(basePath + "/users/" + "user1_id" + "/raw"))
                .header("Authorization", "Bearer " + VALID_TOKEN)
                .method("PUT", HttpRequest.BodyPublishers.ofByteArray(file))
                .build(),
            HttpResponse.BodyHandlers.ofString());

    assertEquals(HttpStatus.OK.value(), response.statusCode());
    assertEquals(FILE_ID, response.body());
  }

  @Test
  void upload_user_file_ko() throws IOException, InterruptedException {
    when(fileService.uploadFile(any(), any())).thenReturn(FILE_ID);

    HttpClient unauthenticatedClient = HttpClient.newBuilder().build();
    String basePath = "http://localhost:" + FileControllerIT.ContextInitializer.SERVER_PORT;

    HttpResponse<String> response =
        unauthenticatedClient.send(
            HttpRequest.newBuilder()
                .uri(URI.create(basePath + "/users/" + FILE_ID + "/raw"))
                .header("Authorization", "Bearer " + VALID_TOKEN)
                .method("PUT", HttpRequest.BodyPublishers.ofByteArray(file))
                .build(),
            HttpResponse.BodyHandlers.ofString());

    assertEquals(HttpStatus.FORBIDDEN.value(), response.statusCode());
  }

  static class ContextInitializer extends AbstractContextInitializer {
    public static final int SERVER_PORT = anAvailablePort();

    @Override
    public int getServerPort() {
      return SERVER_PORT;
    }
  }
}
