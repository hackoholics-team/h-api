package app.hackoholics.api.service;

import app.hackoholics.api.model.Payment;
import app.hackoholics.api.model.PaymentMethod;
import app.hackoholics.api.model.exception.NotFoundException;
import app.hackoholics.api.repository.PaymentMethodRepository;
import app.hackoholics.api.repository.PaymentRepository;
import app.hackoholics.api.service.stripe.StripeConf;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PaymentService {
  private final PaymentMethodRepository payMethodRepository;
  private final StripeConf stripeConf;
  private final PaymentRepository paymentRepository;

  @SneakyThrows
  public PaymentMethod crupdate(PaymentMethod toSave) {
    var stripeMethod = stripeConf.createPaymentMethod(toSave);
    toSave.setId(stripeMethod.getId());
    return payMethodRepository.save(toSave);
  }

  public List<PaymentMethod> getPaymentMethods(String userId) {
    return payMethodRepository.findByUserId(userId);
  }

  @SneakyThrows
  public Payment initiatePayment(Payment payment) {
    stripeConf.initiatePayment(payment);
    return paymentRepository.save(payment);
  }

  public PaymentMethod getPaymentMethod(String pmId) {
    return payMethodRepository
        .findById(pmId)
        .orElseThrow(() -> new NotFoundException("Payment method.id=" + pmId + " is not found."));
  }
}
