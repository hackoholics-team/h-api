package app.hackoholics.api.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Entity
@Table(name = "\"user\"")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class User implements Serializable {
  @Id
  private String id;
  private String name;
  private String firebaseId;
  private String email;
  private String photoId;
}
