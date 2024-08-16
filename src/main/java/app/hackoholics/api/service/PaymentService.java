package app.hackoholics.api.service;

import app.hackoholics.api.model.Payment;
import app.hackoholics.api.model.PaymentMethod;
import app.hackoholics.api.repository.PaymentMethodRepository;
import app.hackoholics.api.repository.PaymentRepository;
import app.hackoholics.api.service.stripe.StripeConf;
import com.stripe.model.checkout.Session;
import com.stripe.param.PaymentMethodCreateParams;
import com.stripe.param.checkout.SessionCreateParams;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.stripe.param.PaymentMethodCreateParams.Type.CARD;
import static com.stripe.param.checkout.SessionCreateParams.Mode.PAYMENT;

@Service
@AllArgsConstructor
public class PaymentService {
  private final PaymentMethodRepository payMethodRepository;
  private final StripeConf stripeConf;
  private final PaymentRepository paymentRepository;

  @SneakyThrows
  public PaymentMethod crupdate(PaymentMethod toSave){
    var card = PaymentMethodCreateParams.CardDetails.builder()
        .setNumber(toSave.getNumber())
        .setCvc(toSave.getCvc())
        .setExpMonth((long) toSave.getExpMonth())
        .setExpYear((long) toSave.getExpYear())
        .build();
    var paymentMethod = PaymentMethodCreateParams.builder()
        .setType(CARD)
        .setCard(card).build();
    var stripeMethod = com.stripe.model.PaymentMethod
        .create(paymentMethod, stripeConf.getOptions());
    toSave.setId(stripeMethod.getId());
    return payMethodRepository.save(toSave);
  }

  public List<PaymentMethod> getPaymentMethods(String userId){
    return payMethodRepository.findByUserId(userId);
  }

  @SneakyThrows
  public Payment initiatePayment(Payment payment){
    var pay = SessionCreateParams.builder()
        .addLineItem(SessionCreateParams.LineItem.builder()
            .setPrice(String.valueOf(payment.getAmount() / 100))
            .build())
        .setCurrency(payment.getCurrency())
        .setMode(PAYMENT)
        .build();
    Session.create(pay, stripeConf.getOptions());
    return paymentRepository.save(payment);
  }

}
