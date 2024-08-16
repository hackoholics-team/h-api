package app.hackoholics.api.endpoint.controller;

import app.hackoholics.api.endpoint.mapper.PaymentMethodRestMapper;
import app.hackoholics.api.endpoint.rest.model.PaymentMethod;
import app.hackoholics.api.service.BillingService;
import com.stripe.exception.StripeException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@Slf4j
public class BillingController {
  private final PaymentMethodRestMapper paymentMethodRestMapper;
  private final BillingService service;

  @GetMapping("/users/{userId}/paymentMethods/{paymentMethodId}")
  public PaymentMethod getPaymentMethod(
      @PathVariable("userId") String userId,
      @PathVariable("paymentMethodId") String paymentMethodId)
      throws StripeException {
    return paymentMethodRestMapper.toRest(service.getPaymentMethod(paymentMethodId));
  }
}
