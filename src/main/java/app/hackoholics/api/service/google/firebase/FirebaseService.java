package app.hackoholics.api.service.google.firebase;

import app.hackoholics.api.model.exception.ForbiddenException;
import app.hackoholics.api.service.google.GoogleConf;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class FirebaseService {
  private static final Object lock = new Object();
  private static boolean firebaseAppInitialized = false;
  private final GoogleConf googleConf;

  private void initializeFirebaseApp() {
    synchronized (lock) {
      if (!firebaseAppInitialized) {
        FirebaseOptions options =
            FirebaseOptions.builder().setCredentials(googleConf.getFirebaseCredentials()).build();
        FirebaseApp.initializeApp(options);
        firebaseAppInitialized = true;
      }
    }
  }

  @SneakyThrows
  private FirebaseAuth auth() {
    initializeFirebaseApp();
    return FirebaseAuth.getInstance();
  }

  public FirebaseUser getUserByBearer(String bearer) {
    if (bearer == null || bearer.isEmpty()) {
      throw new IllegalArgumentException("Bearer token must not be null or empty");
    }
    try {
      FirebaseToken token = auth().verifyIdToken(bearer);
      return new FirebaseUser(token.getEmail(), token.getUid());
    } catch (FirebaseAuthException e) {
      throw new ForbiddenException(e.getMessage());
    }
  }
}
