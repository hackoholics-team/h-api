package app.hackoholics.api.service.google;

import com.google.auth.oauth2.GoogleCredentials;
import java.io.ByteArrayInputStream;
import lombok.Getter;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GoogleConf {
  @Getter private final String projectId;
  private final String firebaseAccessKey;
  private final String projectAccessKey;

  public GoogleConf(
      @Value("${google.project.id}") String pId,
      @Value("${firebase.access.key}") String firebaseAccessKey,
      @Value("${project.access.key}") String projectAccessKey) {
    this.firebaseAccessKey = firebaseAccessKey;
    this.projectAccessKey = projectAccessKey;
    this.projectId = pId;
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
