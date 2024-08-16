package app.hackoholics.api.service.google.gemini;

import app.hackoholics.api.service.google.GoogleConf;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.vertexai.VertexAI;
import com.google.cloud.vertexai.generativeai.GenerativeModel;
import com.google.cloud.vertexai.generativeai.ResponseHandler;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class GeminiService {
  private static final String PROMPT_TEMPLATE =
      "Why is %s a better place for me according to this following requirements %s in 10 lines";
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
  private GenerativeModel getModel() {
    var vertexAi =
        new VertexAI.Builder()
            .setProjectId(googleConf.getProjectId())
            .setLocation(location)
            .setCredentials(GoogleCredentials.getApplicationDefault())
            .build();
    return new GenerativeModel(modelType, vertexAi);
  }

  @SneakyThrows
  public String describe(String place, String requirements) {
    var content = getModel().generateContent(String.format(PROMPT_TEMPLATE, place, requirements));
    return ResponseHandler.getText(content);
  }
}
