package app.hackoholics.api.endpoint.controller;

import app.hackoholics.api.service.ChatService;
import java.util.List;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ChatController {
  private final ChatService service = ChatService.getInstance();

  @PostMapping("/chat")
  public List<String> sendMessage(@RequestBody String message) {
    return service.sendMessage(message);
  }
}
