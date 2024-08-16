package app.hackoholics.api.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name = "\"payment\"")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Payment {
  @Id private String id;
  private int amount;

  @CreationTimestamp private Instant creationDatetime;
  private String currency;

  private String paymentMethodId;
}
