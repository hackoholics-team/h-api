package app.hackoholics.api.model;

import static org.hibernate.type.SqlTypes.NAMED_ENUM;

import jakarta.persistence.*;
import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;

@Entity
@Table(name = "\"subscription\"")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Subscription {
  @Id private String id;
  private Instant creationDatetime;
  private String userId;

  @JdbcTypeCode(NAMED_ENUM)
  private app.hackoholics.api.endpoint.rest.model.Subscription.SubscribeTypeEnum subscriptionType;
}
