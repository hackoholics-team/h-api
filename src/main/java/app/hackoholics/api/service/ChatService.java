package app.hackoholics.api.service;

import app.hackoholics.api.model.exception.BadRequestException;
import app.hackoholics.api.service.google.gemini.GeminiService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatService {
  private final GeminiService geminiService;

  public List<String> sendMessage(String message, byte[] file) {
    if (message == null || file == null) {
      throw new BadRequestException("Message and file cannot be both null");
    }
    return geminiService.sendMessage(message, file);
  }
}
