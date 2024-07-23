package app.hackoholics.api.service.google;

import com.google.auth.oauth2.GoogleCredentials;
import java.io.ByteArrayInputStream;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GoogleConf {
  private final String firebaseAccessKey;
  private final String projectAccessKey;

  public GoogleConf(
      @Value("${firebase.access.key}") String firebaseAccessKey,
      @Value("${project.access.key}") String projectAccessKey) {
    this.firebaseAccessKey = firebaseAccessKey;
    this.projectAccessKey = projectAccessKey;
  }

  @SneakyThrows
  public GoogleCredentials getFirebaseCredentials() {
    var stream = new ByteArrayInputStream(firebaseAccessKey.getBytes());
    return GoogleCredentials.fromStream(stream);
  }

  @SneakyThrows
  public GoogleCredentials getProjectCredentials() {
    var stream = new ByteArrayInputStream(projectAccessKey.getBytes());
    return GoogleCredentials.fromStream(stream);
  }
}
