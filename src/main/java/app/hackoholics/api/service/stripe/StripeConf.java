package app.hackoholics.api.service.stripe;

import static com.stripe.param.PaymentMethodCreateParams.Type.CARD;
import static com.stripe.param.checkout.SessionCreateParams.Mode.PAYMENT;

import app.hackoholics.api.model.Payment;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentMethod;
import com.stripe.model.checkout.Session;
import com.stripe.net.RequestOptions;
import com.stripe.param.PaymentMethodCreateParams;
import com.stripe.param.checkout.SessionCreateParams;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StripeConf {
  private final String apiKey;

  public StripeConf(@Value("${stripe.api.key}") String apiKey) {
    this.apiKey = apiKey;
  }

  public RequestOptions getOptions() {
    return RequestOptions.builder().setApiKey(apiKey).build();
  }

  public PaymentMethod createPaymentMethod(app.hackoholics.api.model.PaymentMethod toSave)
      throws StripeException {
    var card =
        PaymentMethodCreateParams.CardDetails.builder()
            .setNumber(toSave.getNumber())
            .setCvc(toSave.getCvc())
            .setExpMonth((long) toSave.getExpMonth())
            .setExpYear((long) toSave.getExpYear())
            .build();
    var paymentMethod = PaymentMethodCreateParams.builder().setType(CARD).setCard(card).build();
    return com.stripe.model.PaymentMethod.create(paymentMethod, getOptions());
  }

  public void initiatePayment(Payment payment) throws StripeException {
    var pay =
        SessionCreateParams.builder()
            .addLineItem(
                SessionCreateParams.LineItem.builder()
                    .setPrice(String.valueOf(payment.getAmount() / 100))
                    .build())
            .setCurrency(payment.getCurrency())
            .setMode(PAYMENT)
            .build();
    Session.create(pay, getOptions());
  }
}
