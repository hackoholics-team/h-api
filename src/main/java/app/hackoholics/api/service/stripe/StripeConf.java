package app.hackoholics.api.service.stripe;

import static java.util.UUID.randomUUID;

import app.hackoholics.api.endpoint.security.AuthProvider;
import app.hackoholics.api.model.Payment;
import app.hackoholics.api.model.User;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Customer;
import com.stripe.model.PaymentIntent;
import com.stripe.model.PaymentMethod;
import com.stripe.net.RequestOptions;
import com.stripe.param.CustomerCreateParams;
import com.stripe.param.PaymentIntentCreateParams;
import com.stripe.param.PaymentMethodAttachParams;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StripeConf {
  private final String apiKey;

  public StripeConf(@Value("${stripe.api.key}") String apiKey) {
    this.apiKey = apiKey;
    Stripe.apiKey = apiKey;
  }

  public RequestOptions getOptions() {
    return RequestOptions.builder().setApiKey(apiKey).build();
  }

  @SneakyThrows
  public Customer createCustomer(User user) {
    var name = user.getUsername() == null ? "Customer" + randomUUID() : user.getUsername();
    CustomerCreateParams params =
        CustomerCreateParams.builder().setEmail(user.getEmail()).setName(name).build();
    return Customer.create(params, getOptions());
  }

  public void createPaymentMethod(app.hackoholics.api.model.PaymentMethod toSave)
      throws StripeException {
    PaymentMethodAttachParams params =
        PaymentMethodAttachParams.builder().setCustomer(toSave.getUser().getStripeId()).build();

    PaymentMethod paymentMethod = PaymentMethod.retrieve(toSave.getId());
    paymentMethod.attach(params, getOptions());
  }

  public void initiatePayment(Payment payment) throws StripeException {
    PaymentIntentCreateParams params =
        PaymentIntentCreateParams.builder()
            .setAmount((long) payment.getAmount())
            .setCurrency(payment.getCurrency())
            .setPaymentMethod(payment.getPaymentMethodId())
            .setCustomer(AuthProvider.getUser().getStripeId())
            .setConfirm(true)
            .setOffSession(true)
            .build();
    PaymentIntent.create(params, getOptions());
  }
}
