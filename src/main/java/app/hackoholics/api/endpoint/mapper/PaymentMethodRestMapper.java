package app.hackoholics.api.endpoint.mapper;

import app.hackoholics.api.endpoint.rest.model.PaymentMethod;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class PaymentMethodRestMapper {
  private final CardRestMapper cardRestMapper;

  public PaymentMethod toRest(app.hackoholics.api.model.billing.PaymentMethod domain) {
    return new PaymentMethod().id(domain.getId()).card(cardRestMapper.toRest(domain.getCard()));
  }

  public app.hackoholics.api.model.billing.PaymentMethod toDomain(PaymentMethod rest) {
    return new app.hackoholics.api.model.billing.PaymentMethod(
        rest.getId(), cardRestMapper.toDomain(rest.getCard()));
  }
}
