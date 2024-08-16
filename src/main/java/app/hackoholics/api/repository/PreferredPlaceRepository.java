package app.hackoholics.api.repository;

import app.hackoholics.api.model.PreferredPlace;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PreferredPlaceRepository extends JpaRepository<PreferredPlace, String> {
  List<PreferredPlace> findByUserId(String userId);
}
