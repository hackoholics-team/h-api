package app.hackoholics.api.service.google.gemini;

import app.hackoholics.api.service.google.GoogleConf;
import com.google.cloud.vertexai.VertexAI;
import com.google.cloud.vertexai.generativeai.GenerativeModel;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class GeminiService {
  private final String location;
  private final String modelType;
  private final GoogleConf googleConf;

  public GeminiService(
      @Value("${gemini.location}") String loc,
      @Value("${gemini.type}") String type,
      GoogleConf conf) {
    location = loc;
    modelType = type;
    this.googleConf = conf;
  }

  @SneakyThrows
  public GenerativeModel getModel() {
    var vertexAi =
        new VertexAI.Builder()
            .setProjectId(googleConf.getProjectId())
            .setLocation(location)
            .setCredentials(googleConf.getProjectCredentials())
            .build();
    return new GenerativeModel(modelType, vertexAi);
  }
}
