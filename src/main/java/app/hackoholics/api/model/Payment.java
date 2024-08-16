package app.hackoholics.api.model;

import static jakarta.persistence.CascadeType.ALL;
import static jakarta.persistence.FetchType.LAZY;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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

  @ManyToOne(cascade = ALL, fetch = LAZY)
  @JoinColumn(name = "payment_method_id")
  private PaymentMethod paymentMethod;
}
