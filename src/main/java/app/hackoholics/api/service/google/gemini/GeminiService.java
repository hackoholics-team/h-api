package app.hackoholics.api.service.google.gemini;

import static app.hackoholics.api.file.FileTyper.parseMediaTypeFromBytes;

import app.hackoholics.api.service.google.GoogleConf;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.vertexai.VertexAI;
import com.google.cloud.vertexai.api.Part;
import com.google.cloud.vertexai.generativeai.ChatSession;
import com.google.cloud.vertexai.generativeai.ContentMaker;
import com.google.cloud.vertexai.generativeai.GenerativeModel;
import com.google.cloud.vertexai.generativeai.PartMaker;
import java.util.List;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class GeminiService {
  private final ChatSession session;
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
    this.session = new ChatSession(getModel());
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
  public List<String> sendMessage(String message, byte[] file) {
    var request =
        ContentMaker.fromMultiModalData(
            message, PartMaker.fromMimeTypeAndData(parseMediaTypeFromBytes(file).toString(), file));
    session.sendMessage(request);
    return session.getHistory().stream()
        .flatMap(content -> content.getPartsList().stream().map(Part::getText))
        .toList();
  }
}
