package app.hackoholics.api.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "\"preferred_place\"")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class PreferredPlace implements Serializable {
  @Id private String id;
  private String userId;
}
