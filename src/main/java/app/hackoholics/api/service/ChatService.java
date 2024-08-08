package app.hackoholics.api.service;

import app.hackoholics.api.service.google.gemini.GeminiService;
import com.google.cloud.vertexai.api.Part;
import java.util.List;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

@Service
public class ChatService {
  private static ChatService INSTANCE;
  private static GeminiService geminiService;

  private ChatService(GeminiService geminiService) {
    ChatService.geminiService = geminiService;
  }

  public static ChatService getInstance() {
    if (INSTANCE == null) {
      return new ChatService(geminiService);
    }
    return INSTANCE;
  }

  @SneakyThrows
  public List<String> sendMessage(String message) {
    var session = geminiService.chatSession();
    session.sendMessage(message);
    var history = session.getHistory();
    return history.stream()
        .flatMap(content -> content.getPartsList().stream().map(Part::getText))
        .toList();
  }
}
