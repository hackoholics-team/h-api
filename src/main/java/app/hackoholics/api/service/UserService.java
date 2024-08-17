package app.hackoholics.api.service;

import app.hackoholics.api.model.User;
import app.hackoholics.api.repository.UserRepository;
import app.hackoholics.api.service.stripe.StripeConf;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {
  private final UserRepository repository;
  private final StripeConf stripeConf;

  public User getByEmailAndAuthenticationId(String email, String authId) {
    return repository
        .findByEmailAndFirebaseId(email, authId)
        .orElseThrow(
            () ->
                new BadCredentialsException(
                    "User.email=" + email + " should complete inscription"));
  }

  public User save(User user) {
    var customerId = stripeConf.createCustomer(user).getId();
    user.setStripeId(customerId);
    return repository.save(user);
  }
}
