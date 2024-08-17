package app.hackoholics.api.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.time.Instant;
import jdk.jfr.Timestamp;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name = "\"user\"")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class User implements Serializable {
  @Id private String id;
  private String firstName;
  private String lastName;
  private String username;
  private String stripeId;
  @Timestamp private Instant birthDate;
  @CreationTimestamp private Instant creationDatetime;
  private String firebaseId;
  private String email;
  private String photoId;
}
