package app.hackoholics.api.model;

import static org.hibernate.type.SqlTypes.JSON;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;

@Entity
@Table(name = "\"requirement\"")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class Requirement implements Serializable {
  @Id
  // id=userId
  private String id;

  @JdbcTypeCode(JSON)
  private List<String> requirements = new ArrayList<>();
}
