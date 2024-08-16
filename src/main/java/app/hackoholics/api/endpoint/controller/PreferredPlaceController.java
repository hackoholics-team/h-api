package app.hackoholics.api.endpoint.controller;

import app.hackoholics.api.endpoint.mapper.PreferredPlaceRestMapper;
import app.hackoholics.api.endpoint.rest.model.PreferedPlace;
import app.hackoholics.api.service.PreferredPlaceService;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class PreferredPlaceController {
  private final PreferredPlaceService service;
  private final PreferredPlaceRestMapper mapper;

  @GetMapping("/users/{id}/preferences")
  public List<PreferedPlace> getPreferences(@PathVariable("id") String uId) {
    return service.getPreferredPlace(uId).stream().map(mapper::toRest).toList();
  }

  @PutMapping("/users/{id}/preferences")
  public List<PreferedPlace> crupdatePreferences(
      @PathVariable("id") String uId, @RequestBody List<PreferedPlace> places) {
    var toSave = places.stream().map(mapper::toDomain).toList();
    return service.crupdatePreferredPlace(toSave).stream().map(mapper::toRest).toList();
  }
}
