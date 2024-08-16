package app.hackoholics.api.model.billing;

import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "\"paymentMethod\"")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class PaymentMethod {
  @Id private String id;
  private Card card;
}
