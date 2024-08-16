package app.hackoholics.api.model;

import static jakarta.persistence.CascadeType.ALL;
import static jakarta.persistence.FetchType.LAZY;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "\"payment_method\"")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class PaymentMethod {
  @Id private String id;

  @ManyToOne(cascade = ALL, fetch = LAZY)
  @JoinColumn(name = "user_id")
  private User user;

  private String cvc;
  private String brand;
  private String number;
  private int expMonth;
  private int expYear;
}
