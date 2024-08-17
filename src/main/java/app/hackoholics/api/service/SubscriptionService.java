package app.hackoholics.api.service;

import app.hackoholics.api.model.Subscription;
import app.hackoholics.api.repository.SubscriptionRepository;
import java.util.Comparator;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class SubscriptionService {
  private final SubscriptionRepository repository;

  public Subscription crupdate(Subscription subscription) {
    return repository.save(subscription);
  }

  public Subscription getByUserId(String userId) {
    var subscriptions = repository.findByUserId(userId);
    if (subscriptions.isEmpty()) {
      return null;
    }
    return subscriptions.stream()
        .sorted(Comparator.comparing(Subscription::getCreationDatetime))
        .toList()
        .getFirst();
  }
}
