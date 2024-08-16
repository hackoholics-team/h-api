package app.hackoholics.api.model;

import jakarta.persistence.*;
import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "\"subscribe\"")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Subscription {
  @Id private String id;
  private Instant creationDtatetime;

  @ManyToOne
  @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
  private User user;

  @Enumerated(EnumType.STRING)
  @Column(name = "subscribe_type")
  private app.hackoholics.api.endpoint.rest.model.Subscription.SubscribeTypeEnum subscribeTypeEnum;
}
