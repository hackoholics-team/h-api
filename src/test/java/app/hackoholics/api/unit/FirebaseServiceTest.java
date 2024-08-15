package app.hackoholics.api.unit;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;

import app.hackoholics.api.service.google.GoogleConf;
import app.hackoholics.api.service.google.firebase.FirebaseService;
import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.BadCredentialsException;

class FirebaseServiceTest {
  GoogleConf googleConf = mock();
  FirebaseService subject = new FirebaseService(googleConf);

  @Test
  void read_user_by_bearer_ko() {
    assertThrows(BadCredentialsException.class, () -> subject.getUserByBearer(null));
  }
}
