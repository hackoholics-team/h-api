package app.hackoholics.api.model.billing;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Card {
  @Id private String id;
  private String brand;
  private String number;
  private int expMonth;
  private int expYear;
}
