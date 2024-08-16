package app.hackoholics.api.service;

import app.hackoholics.api.model.PreferredPlace;
import app.hackoholics.api.repository.PreferredPlaceRepository;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PreferredPlaceService {
  private final PreferredPlaceRepository repository;

  public List<PreferredPlace> crupdatePreferredPlace(List<PreferredPlace> toSave) {
    return repository.saveAll(toSave);
  }

  public List<PreferredPlace> getPreferredPlace(String userId) {
    return repository.findByUserId(userId);
  }
}
