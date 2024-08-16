package app.hackoholics.api.service.stripe;

import app.hackoholics.api.endpoint.security.AuthProvider;
import app.hackoholics.api.model.Payment;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.model.PaymentMethod;
import com.stripe.net.RequestOptions;
import com.stripe.param.PaymentIntentCreateParams;
import com.stripe.param.PaymentMethodAttachParams;
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

  public void createPaymentMethod(app.hackoholics.api.model.PaymentMethod toSave)
      throws StripeException {
    PaymentMethodAttachParams params =
        PaymentMethodAttachParams.builder().setCustomer(toSave.getUser().getId()).build();

    PaymentMethod paymentMethod = PaymentMethod.retrieve(toSave.getId());
    paymentMethod.attach(params, getOptions());
  }

  public void initiatePayment(Payment payment) throws StripeException {
    PaymentIntentCreateParams params =
        PaymentIntentCreateParams.builder()
            .setAmount((long) payment.getAmount())
            .setCurrency(payment.getCurrency())
            .setPaymentMethod(payment.getPaymentMethodId())
            .setCustomer(AuthProvider.getUser().getId())
            .setConfirm(true)
            .setOffSession(true)
            .build();
    PaymentIntent.create(params, getOptions());
  }
}
