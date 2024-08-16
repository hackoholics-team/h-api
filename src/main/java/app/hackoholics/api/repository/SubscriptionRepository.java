package app.hackoholics.api.repository;

import app.hackoholics.api.model.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubscriptionRepository extends JpaRepository<Subscription, String> {}
