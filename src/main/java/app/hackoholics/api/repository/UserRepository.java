package app.hackoholics.api.repository;

import app.hackoholics.api.model.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
  Optional<User> findByEmailAndFirebaseId(String email, String fId);
}
