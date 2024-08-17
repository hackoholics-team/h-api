package app.hackoholics.api.unit.model;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import app.hackoholics.api.model.Requirement;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Test;

class RequirementTest {
  public final ObjectMapper mapper = new ObjectMapper();

  @Test
  void serialize_user() throws JsonProcessingException {
    var requirement = new Requirement("dummy", List.of("dummy"));
    var serialized = mapper.writeValueAsString(requirement);
    var deserialized = mapper.readValue(serialized, Requirement.class);

    assertEquals(requirement, deserialized);
  }

  @Test
  public void testBuilderAndGetters() {
    // Utiliser le Builder pour créer une instance de Requirement
    Requirement requirement =
        Requirement.builder()
            .id("user123")
            .requirements(Arrays.asList("Requirement 1", "Requirement 2"))
            .build();

    // Vérifier que les valeurs sont correctement assignées
    assertEquals("user123", requirement.getId());
    assertTrue(requirement.getRequirements().contains("Requirement 1"));
    assertTrue(requirement.getRequirements().contains("Requirement 2"));
  }

  @Test
  public void testNoArgsConstructor() {
    // Créer une instance avec le constructeur sans arguments
    Requirement requirement = new Requirement();

    // Vérifier que les valeurs par défaut sont correctement assignées
    assertTrue(requirement.getId() == null);
    assertEquals(Collections.emptyList(), requirement.getRequirements());
  }

  @Test
  public void testAllArgsConstructor() {
    // Créer une instance avec le constructeur avec tous les arguments
    Requirement requirement =
        new Requirement("user123", Arrays.asList("Requirement 1", "Requirement 2"));

    // Vérifier que les valeurs sont correctement assignées
    assertEquals("user123", requirement.getId());
    assertTrue(requirement.getRequirements().contains("Requirement 1"));
    assertTrue(requirement.getRequirements().contains("Requirement 2"));
  }

  @Test
  public void testSettersAndGetters() {
    // Créer une instance et utiliser les setters pour assigner des valeurs
    Requirement requirement = new Requirement();
    requirement.setId("user123");
    requirement.setRequirements(Arrays.asList("Requirement 1", "Requirement 2"));

    // Vérifier que les getters retournent les bonnes valeurs
    assertEquals("user123", requirement.getId());
    assertTrue(requirement.getRequirements().contains("Requirement 1"));
    assertTrue(requirement.getRequirements().contains("Requirement 2"));
  }

  @Test
  public void testEqualsAndHashCode() {
    // Créer deux instances identiques de Requirement
    Requirement requirement1 =
        Requirement.builder()
            .id("user123")
            .requirements(Arrays.asList("Requirement 1", "Requirement 2"))
            .build();

    Requirement requirement2 =
        Requirement.builder()
            .id("user123")
            .requirements(Arrays.asList("Requirement 1", "Requirement 2"))
            .build();

    // Vérifier que les deux instances sont égales
    assertEquals(requirement2, requirement1);
    assertEquals(requirement1.hashCode(), requirement2.hashCode());
  }

  @Test
  public void testToString() {
    // Créer une instance de Requirement
    Requirement requirement =
        Requirement.builder()
            .id("user123")
            .requirements(Arrays.asList("Requirement 1", "Requirement 2"))
            .build();

    // Vérifier que la méthode toString produit le résultat attendu
    assertTrue(requirement.toString().contains("user123"));
    assertTrue(requirement.toString().contains("Requirement 1"));
    assertTrue(requirement.toString().contains("Requirement 2"));
  }
}
