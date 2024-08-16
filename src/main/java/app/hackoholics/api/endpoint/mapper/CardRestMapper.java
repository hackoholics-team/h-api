package app.hackoholics.api.endpoint.mapper;

import app.hackoholics.api.endpoint.rest.model.Card;
import org.springframework.stereotype.Component;

@Component
public class CardRestMapper {
  public Card toRest(app.hackoholics.api.model.billing.Card domain) {
    return new Card()
        .id(domain.getId())
        .expMonth(domain.getExpMonth())
        .expYear(domain.getExpYear())
        .brand(domain.getBrand())
        .number(domain.getNumber());
  }

  public app.hackoholics.api.model.billing.Card toDomain(Card rest) {
    return new app.hackoholics.api.model.billing.Card(
        rest.getId(), rest.getBrand(), rest.getNumber(), rest.getExpMonth(), rest.getExpYear());
  }
}
