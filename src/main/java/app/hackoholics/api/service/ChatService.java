package app.hackoholics.api.service;

import app.hackoholics.api.service.google.gemini.GeminiService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatService {
  private final GeminiService geminiService;

  public List<String> sendMessage(String message) {
    return geminiService.sendMessage(message);
  }
}
