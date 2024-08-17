package app.hackoholics.api.endpoint.mapper;

import app.hackoholics.api.endpoint.rest.model.Payment;
import app.hackoholics.api.endpoint.rest.model.PaymentMethod;
import app.hackoholics.api.endpoint.security.AuthProvider;
import org.springframework.stereotype.Component;

@Component
public class PaymentRestMapper {
  public PaymentMethod toRest(app.hackoholics.api.model.PaymentMethod domain) {
    return new PaymentMethod()
        .id(domain.getId())
        .brand(domain.getBrand())
        .number(domain.getNumber())
        .cvc(domain.getCvc())
        .expYear(domain.getExpYear())
        .expMonth(domain.getExpMonth());
  }

  public app.hackoholics.api.model.PaymentMethod toDomain(PaymentMethod domain) {
    return app.hackoholics.api.model.PaymentMethod.builder()
        .id(domain.getId())
        .user(AuthProvider.getUser())
        .brand(domain.getBrand())
        .number(domain.getNumber())
        .cvc(domain.getCvc())
        .expYear(domain.getExpYear())
        .expMonth(domain.getExpMonth())
        .build();
  }

  public Payment toRest(app.hackoholics.api.model.Payment domain) {
    return new Payment()
        .id(domain.getId())
        .creationDatetime(domain.getCreationDatetime())
        .paymentMethodId(domain.getPaymentMethodId())
        .amount(domain.getAmount())
        .currency(domain.getCurrency());
  }

  public app.hackoholics.api.model.Payment toDomain(Payment domain) {
    return app.hackoholics.api.model.Payment.builder()
        .id(domain.getId())
        .creationDatetime(domain.getCreationDatetime())
        .paymentMethodId(domain.getPaymentMethodId())
        .amount(domain.getAmount())
        .currency(domain.getCurrency())
        .build();
  }
}
