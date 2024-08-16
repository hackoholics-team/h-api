package app.hackoholics.api.unit.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import app.hackoholics.api.model.Requirement;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
}
