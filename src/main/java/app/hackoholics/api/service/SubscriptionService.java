package app.hackoholics.api.service;

import app.hackoholics.api.model.Subscription;
import app.hackoholics.api.repository.SubscriptionRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class SubscriptionService {
  private final SubscriptionRepository repository;

  public Subscription crupdateSubscription(Subscription subscription) {
    return repository.save(subscription);
  }
}
