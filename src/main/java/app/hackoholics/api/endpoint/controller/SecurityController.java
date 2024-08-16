package app.hackoholics.api.endpoint.controller;

import app.hackoholics.api.endpoint.mapper.UserRestMapper;
import app.hackoholics.api.endpoint.rest.model.InlineObject;
import app.hackoholics.api.endpoint.rest.model.User;
import app.hackoholics.api.endpoint.security.AuthProvider;
import app.hackoholics.api.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@Slf4j
public class SecurityController {
  private final UserService userService;
  private final UserRestMapper mapper;

  @PostMapping(value = "/signin")
  public User signIn() {
    log.info("Auth {}", AuthProvider.getUser());
    return mapper.toRest(AuthProvider.getUser());
  }

  @PostMapping(value = "/signup")
  public User signUp(@RequestBody User toCreate) {
    var toSave = mapper.toDomain(toCreate);
    return mapper.toRest(userService.save(toSave));
  }

  @PostMapping(value = "/processing")
  public boolean isSignupStillProcessed(@RequestBody InlineObject payload) {
    try {
      userService.getByEmailAndAuthenticationId(payload.getEmail(), payload.getUid());
      return true;
    } catch (Exception e) {
      return false;
    }
  }
}
