package app.hackoholics.api.endpoint.controller;

import app.hackoholics.api.service.RequirementService;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class RequirementController {
  private final RequirementService service;

  @GetMapping("/users/{id}/requirements")
  public List<String> getRequirements(@PathVariable("id") String uId) {
    return service.getRequirements(uId);
  }

  @PutMapping("/users/{id}/requirements")
  public List<String> crupdateRequirements(
      @PathVariable("id") String uId, @RequestBody List<String> requirements) {
    return service.crupdate(uId, requirements);
  }
}
