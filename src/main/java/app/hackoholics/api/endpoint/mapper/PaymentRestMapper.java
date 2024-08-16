package app.hackoholics.api.endpoint.mapper;

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
        .userId(AuthProvider.getUser().getId())
        .brand(domain.getBrand())
        .number(domain.getNumber())
        .cvc(domain.getCvc())
        .expYear(domain.getExpYear())
        .expMonth(domain.getExpMonth())
        .build();
  }
}
