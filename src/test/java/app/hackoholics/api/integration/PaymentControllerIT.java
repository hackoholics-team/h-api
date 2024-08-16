package app.hackoholics.api.integration;

import static app.hackoholics.api.conf.TestConf.VALID_TOKEN;
import static app.hackoholics.api.conf.TestConf.anAvailablePort;
import static app.hackoholics.api.conf.TestConf.setUpFirebaseService;
import static app.hackoholics.api.conf.TestConf.user;
import static java.util.UUID.randomUUID;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

import app.hackoholics.api.AbstractContextInitializer;
import app.hackoholics.api.conf.TestConf;
import app.hackoholics.api.endpoint.rest.api.PayingApi;
import app.hackoholics.api.endpoint.rest.client.ApiClient;
import app.hackoholics.api.endpoint.rest.client.ApiException;
import app.hackoholics.api.endpoint.rest.model.PaymentMethod;
import app.hackoholics.api.service.FileService;
import app.hackoholics.api.service.google.GoogleConf;
import app.hackoholics.api.service.google.firebase.FirebaseService;
import app.hackoholics.api.service.google.gemini.GeminiService;
import app.hackoholics.api.service.stripe.StripeConf;
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
@ContextConfiguration(initializers = PaymentControllerIT.ContextInitializer.class)
@AutoConfigureMockMvc
class PaymentControllerIT {
  @MockBean GoogleConf googleConf;
  @MockBean GeminiService geminiService;
  @MockBean FileService fileService;
  @MockBean FirebaseService firebaseService;
  @MockBean StripeConf stripeConf;

  private ApiClient anApiClient(String token) {
    return TestConf.anApiClient(token, PaymentControllerIT.ContextInitializer.SERVER_PORT);
  }

  PaymentMethod paymentMethod() {
    return new PaymentMethod()
        .id("dummy")
        .number("123456789")
        .cvc("123")
        .expMonth(6)
        .expYear(2026)
        .brand("Visa");
  }

  @Test
  void get_and_crupadate_payment_method_ok() throws ApiException {
    ApiClient client = anApiClient(VALID_TOKEN);
    PayingApi api = new PayingApi(client);

    var actual = api.crupdatePaymentMethod(user().getId(), paymentMethod());
    var persisted = api.getPaymentMethods(user().getId());
    var unique = api.getPaymentMethod(user().getId(), paymentMethod().getId());

    assertEquals(unique, actual);
    assertTrue(persisted.contains(actual));
  }

  @Test
  void read_payment_method_ko() throws ApiException {
    ApiClient client = anApiClient(VALID_TOKEN);
    PayingApi api = new PayingApi(client);

    assertThrows(
        ApiException.class, () -> api.getPaymentMethod(user().getId(), randomUUID().toString()));
  }

  @BeforeEach
  void setUp() throws StripeException {
    setUpFirebaseService(firebaseService);
    doNothing().when(stripeConf).initiatePayment(any());
    doNothing().when(stripeConf).createPaymentMethod(any());
  }

  static class ContextInitializer extends AbstractContextInitializer {
    public static final int SERVER_PORT = anAvailablePort();

    @Override
    public int getServerPort() {
      return SERVER_PORT;
    }
  }
}
