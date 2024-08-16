package app.hackoholics.api.endpoint.controller;

import app.hackoholics.api.endpoint.mapper.PaymentMethodRestMapper;
import app.hackoholics.api.endpoint.rest.model.PaymentMethod;
import app.hackoholics.api.service.PaymentService;
import com.stripe.exception.StripeException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@Slf4j
public class PaymentController {
  private final PaymentMethodRestMapper paymentMethodRestMapper;
  private final PaymentService service;

  @GetMapping("/users/{uId}/paymentMethods/{pmId}")
  public PaymentMethod getPaymentMethod(
      @PathVariable("uId") String userId,
      @PathVariable("pmId") String paymentMethodId) {
    return null;
  }
}
