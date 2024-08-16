package app.hackoholics.api.service;

import app.hackoholics.api.model.Requirement;
import app.hackoholics.api.repository.RequirementRepository;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RequirementService {
  private final RequirementRepository repository;

  public List<String> getRequirements(String userId) {
    return repository.findById(userId).orElseGet(Requirement::new).getRequirements();
  }

  public List<String> crupdate(String uId, List<String> requirements) {
    return repository.save(new Requirement(uId, requirements)).getRequirements();
  }
}
