package app.hackoholics.api.model.billing;

import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

@Table(name = "\"pay\"")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Pay {
  @Id private String id;
  private int amount;
  @CreationTimestamp private Instant creationDateTime;
  private String currency;
  private PaymentMethod paymentMethod;
}
