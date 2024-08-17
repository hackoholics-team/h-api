package app.hackoholics.api.integration;

import static app.hackoholics.api.conf.TestConf.VALID_TOKEN;
import static app.hackoholics.api.conf.TestConf.anAvailablePort;
import static app.hackoholics.api.conf.TestConf.setUpFirebaseService;
import static app.hackoholics.api.conf.TestConf.user;
import static app.hackoholics.api.endpoint.rest.model.Subscription.SubscribeTypeEnum.PREMIUM;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

import app.hackoholics.api.AbstractContextInitializer;
import app.hackoholics.api.conf.TestConf;
import app.hackoholics.api.endpoint.rest.api.SubscribeApi;
import app.hackoholics.api.endpoint.rest.client.ApiClient;
import app.hackoholics.api.endpoint.rest.client.ApiException;
import app.hackoholics.api.endpoint.rest.model.Subscription;
import app.hackoholics.api.service.FileService;
import app.hackoholics.api.service.google.GoogleConf;
import app.hackoholics.api.service.google.firebase.FirebaseService;
import app.hackoholics.api.service.google.gemini.GeminiService;
import com.stripe.exception.StripeException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@Testcontainers
@ContextConfiguration(initializers = SubscriptionControllerIT.ContextInitializer.class)
@AutoConfigureMockMvc
class SubscriptionControllerIT {
  @MockBean GoogleConf googleConf;
  @MockBean GeminiService geminiService;
  @MockBean FileService fileService;
  @MockBean FirebaseService firebaseService;

  private ApiClient anApiClient(String token) {
    return TestConf.anApiClient(token, SubscriptionControllerIT.ContextInitializer.SERVER_PORT);
  }

  Subscription subscription() {
    return new Subscription().id("dummy").creationDatetime(null).subscribeType(PREMIUM);
  }

  @Test
  void get_and_crupdate_subscription_ok() throws ApiException {
    ApiClient client = anApiClient(VALID_TOKEN);
    SubscribeApi api = new SubscribeApi(client);

    var actual = api.crupdateSubscription(user().getId(), subscription());
    var persisted = api.getSubscription(user().getId());

    assertEquals(persisted, actual);
  }

  @BeforeEach
  void setUp() throws StripeException {
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
