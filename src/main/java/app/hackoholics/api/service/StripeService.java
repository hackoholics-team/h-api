package app.hackoholics.api.service;

import com.google.api.client.util.Value;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.model.PaymentMethod;
import com.stripe.model.Price;
import com.stripe.model.Product;
import com.stripe.param.PaymentIntentCreateParams;
import com.stripe.param.PaymentMethodCreateParams;
import com.stripe.param.PriceCreateParams;
import com.stripe.param.ProductCreateParams;
import com.stripe.param.checkout.SessionCreateParams;
import org.springframework.stereotype.Service;

@Service
public class StripeService {

  @Value("${stripe.apikey}")
  private String apiKey;

  public StripeService() {
    this.apiKey = apiKey;
  }

  public Product createProduct(String name, String description) throws StripeException {
    ProductCreateParams productCreateParams =
        ProductCreateParams.builder().setName(name).setDescription(description).build();
    return Product.create(productCreateParams);
  }

  public Price crupdatePrice(String productId, long unitAmount, String currency)
      throws StripeException {
    PriceCreateParams priceCreateParams =
        PriceCreateParams.builder()
            .setProduct(productId)
            .setCurrency(currency)
            .setUnitAmount(unitAmount)
            .setRecurring(
                PriceCreateParams.Recurring.builder()
                    .setInterval(PriceCreateParams.Recurring.Interval.MONTH)
                    .build())
            .build();
    return Price.create(priceCreateParams);
  }

  public PaymentIntent createPaymentIntent(
      long amount, String currency, String cardNumber, long expMonth, long expYear, String cvc)
      throws StripeException {
    PaymentMethodCreateParams paymentMethodCreateParams =
        PaymentMethodCreateParams.builder()
            .setType(PaymentMethodCreateParams.Type.CARD)
            .setCard(
                PaymentMethodCreateParams.CardDetails.builder()
                    .setNumber(cardNumber)
                    .setExpMonth(expMonth)
                    .setExpYear(expYear)
                    .setCvc(cvc)
                    .build())
            .build();
    PaymentMethod paymentMethod = PaymentMethod.create(paymentMethodCreateParams);

    PaymentIntentCreateParams paymentIntentCreateParams =
        PaymentIntentCreateParams.builder()
            .setPaymentMethod(paymentMethod.getId())
            .setCurrency(currency)
            .setAmount(amount)
            .setConfirm(true)
            .build();
    return PaymentIntent.create(paymentIntentCreateParams);
  }

  public Price createDonnatePrice(String currency, String productId) throws StripeException {
    PriceCreateParams priceCreateParams =
        PriceCreateParams.builder()
            .setCurrency(currency)
            .setCustomUnitAmount(
                PriceCreateParams.CustomUnitAmount.builder().setPreset(5L).setEnabled(true).build())
            .setProduct(productId)
            .build();
    return Price.create(priceCreateParams);
  }

  public void donnate(String priceId, long amount, String returnUrl) {
    SessionCreateParams params =
        SessionCreateParams.builder()
            .addLineItem(
                SessionCreateParams.LineItem.builder()
                    .setPrice(priceId)
                    .setQuantity(amount)
                    .build())
            .setMode(SessionCreateParams.Mode.PAYMENT)
            .setUiMode(SessionCreateParams.UiMode.EMBEDDED)
            .setReturnUrl(returnUrl)
            .build();
  }
}
