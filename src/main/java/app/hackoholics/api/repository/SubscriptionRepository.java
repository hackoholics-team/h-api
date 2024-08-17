package app.hackoholics.api.repository;

import app.hackoholics.api.model.Subscription;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, String> {
  List<Subscription> findByUserId(String userId);
}
