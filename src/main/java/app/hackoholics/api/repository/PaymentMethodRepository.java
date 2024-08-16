package app.hackoholics.api.repository;

import app.hackoholics.api.model.PaymentMethod;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentMethodRepository extends JpaRepository<PaymentMethod, String> {
  List<PaymentMethod> findByUserId(String userId);
}
