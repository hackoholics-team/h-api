package app.hackoholics.api.endpoint.mapper;

import app.hackoholics.api.endpoint.rest.model.Subscription;
import app.hackoholics.api.endpoint.security.AuthProvider;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class SubscriptionMapper {
  private UserRestMapper userRestMapper;

  public Subscription toRest(app.hackoholics.api.model.Subscription domain) {
    return new Subscription()
        .id(domain.getId())
        .creationDatetime(domain.getCreationDatetime())
        .subscribeType(domain.getSubscriptionType());
  }

  public app.hackoholics.api.model.Subscription toDomain(Subscription rest) {
    return app.hackoholics.api.model.Subscription.builder()
        .id(rest.getId())
        .creationDatetime(rest.getCreationDatetime())
        .userId(AuthProvider.getUser().getId())
        .subscriptionType(rest.getSubscribeType())
        .build();
  }
}
