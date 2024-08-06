package app.hackoholics.api.service;

import app.hackoholics.api.service.google.gemini.GeminiService;
import com.google.cloud.vertexai.api.Part;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatService {
  private final GeminiService geminiService;

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
