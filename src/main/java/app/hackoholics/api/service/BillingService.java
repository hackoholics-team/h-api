package app.hackoholics.api.service;

import app.hackoholics.api.model.billing.Card;
import app.hackoholics.api.model.billing.PaymentMethod;
import com.stripe.exception.StripeException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class BillingService {
  @Value("${stripe.apikey}")
  private String apiKey;

  public BillingService() {
    this.apiKey = apiKey;
  }

  /**
   * public Pay pay(Pay pay, String priceId) throws StripeException { SessionCreateParams params =
   * SessionCreateParams.builder() .addLineItem( SessionCreateParams.LineItem.builder()
   * .setPrice(priceId) .setQuantity((long) pay.getAmount()) .build() )
   * .setMode(SessionCreateParams.Mode.PAYMENT)
   * .setCurrency(String.valueOf(SessionCreateParams.UiMode.EMBEDDED)) .build(); Session session =
   * Session.create(params); }*
   */
  public PaymentMethod getPaymentMethod(String paymentMethodId) throws StripeException {
    com.stripe.model.PaymentMethod paymentMethod =
        com.stripe.model.PaymentMethod.retrieve(paymentMethodId);
    com.stripe.model.PaymentMethod.Card cardStripe = paymentMethod.getCard();
    Card card =
        new Card(
            cardStripe.getIin(),
            cardStripe.getBrand(),
            cardStripe.getIin(),
            Math.toIntExact(cardStripe.getExpMonth()),
            Math.toIntExact(cardStripe.getExpYear()));
    return new PaymentMethod(paymentMethod.getId(), card);
  }

  /**
   * public List<PaymentMethod> deletePaymentMethod(String paymentMethodId) throws StripeException {
   * PaymentMethod paymentMethod = getPaymentMethod(paymentMethodId); }
   *
   * <p>public PaymentMethod curpdatePaymentMethod(Card card ) throws StripeException { if
   * (card.getId()!= null){ com.stripe.model.PaymentMethod resource =
   * com.stripe.model.PaymentMethod.retrieve(card.getId()); PaymentMethodUpdateParams params =
   * PaymentMethodUpdateParams.builder() .setCard(PaymentMethodUpdateParams.Card.builder()
   * .setExpMonth((long) card.getExpMonth()) .setExpYear((long) card.getExpYear() ).build())
   * .build(); com.stripe.model.PaymentMethod paymentMethodStripe = resource.update(params); return
   * new PaymentMethod(card.getId(), card); }else { PaymentMethodCreateParams params =
   * PaymentMethodCreateParams.builder() .setType(PaymentMethodCreateParams.Type.CARD) .setCard(
   * PaymentMethodCreateParams.CardDetails.builder() .setNumber(card.getNumber()) .setExpMonth(8L)
   * .setExpYear(2026L) .setCvc("314") .build() ) .build(); } }*
   */
}
