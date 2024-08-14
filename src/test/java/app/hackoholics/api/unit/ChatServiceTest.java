package app.hackoholics.api.unit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import app.hackoholics.api.model.exception.BadRequestException;
import app.hackoholics.api.service.ChatService;
import app.hackoholics.api.service.google.gemini.GeminiService;
import java.util.List;
import org.junit.jupiter.api.Test;

public class ChatServiceTest {
  private final GeminiService geminiService = mock();
  private final ChatService subject = new ChatService(geminiService);

  @Test
  void send_message_ok() {
    when(geminiService.sendMessage(any(), any())).thenReturn(List.of("Hello"));
    var message = "Hi";
    var file = new byte[] {};
    var actual = subject.sendMessage(message, file);

    assertEquals(1, actual.size());
    assertTrue(actual.contains("Hello"));
  }

  @Test
  void send_message_ko() {
    var file = new byte[] {};

    assertThrows(BadRequestException.class, () -> subject.sendMessage(null, file));
  }
}
