package app.hackoholics.api.endpoint.controller;

import app.hackoholics.api.endpoint.mapper.PaymentRestMapper;
import app.hackoholics.api.endpoint.rest.model.Payment;
import app.hackoholics.api.endpoint.rest.model.PaymentMethod;
import app.hackoholics.api.endpoint.validator.PaymentMethodValidator;
import app.hackoholics.api.endpoint.validator.PaymentValidator;
import app.hackoholics.api.service.PaymentService;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@Slf4j
public class PaymentController {
  private final PaymentService service;
  private final PaymentRestMapper mapper;
  private final PaymentMethodValidator paymentMethodValidator;
  private final PaymentValidator paymentValidator;

  @GetMapping("/users/{uId}/paymentMethods")
  public List<PaymentMethod> getPaymentMethods(@PathVariable("uId") String userId) {
    return service.getPaymentMethods(userId).stream().map(mapper::toRest).toList();
  }

  @PutMapping("/users/{uId}/paymentMethods")
  public PaymentMethod crupdatePaymentMethod(
      @PathVariable("uId") String userId, @RequestBody PaymentMethod paymentMethod) {
    paymentMethodValidator.accept(paymentMethod);
    var toSave = mapper.toDomain(paymentMethod);
    return mapper.toRest(service.crupdate(toSave));
  }

  @GetMapping("/users/{uId}/paymentMethods/{pmId}")
  public PaymentMethod crupdatePaymentMethod(
      @PathVariable("uId") String userId, @PathVariable("pmId") String pmId) {
    return mapper.toRest(service.getPaymentMethod(pmId));
  }

  @PostMapping("/users/{uId}/paymentMethods/{pmId}/payments")
  public Payment initiatePayment(
      @PathVariable("uId") String uId,
      @PathVariable("pmId") String pmId,
      @RequestBody Payment payment) {
    paymentValidator.accept(payment);
    var toSave = mapper.toDomain(payment);
    return mapper.toRest(service.initiatePayment(toSave));
  }
}
