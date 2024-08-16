package app.hackoholics.api.endpoint.mapper;

import app.hackoholics.api.endpoint.rest.model.Subscription;
import java.time.format.DateTimeFormatter;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class SubscriptionMapper {
  private UserRestMapper userRestMapper;

  public Subscription toRest(app.hackoholics.api.model.Subscription domain) {
    return new Subscription()
        .id(domain.getId())
        .creationDatetime(domain.getCreationDtatetime())
        .user(userRestMapper.toRest(domain.getUser()))
        .subscribeType(domain.getSubscribeTypeEnum());
  }

  public app.hackoholics.api.model.Subscription toDomain(Subscription rest) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    return new app.hackoholics.api.model.Subscription(
        rest.getId(),
        rest.getCreationDatetime(),
        userRestMapper.toDomain(rest.getUser()),
        rest.getSubscribeType());
  }
}
