package app.hackoholics.api.endpoint.controller;

import app.hackoholics.api.service.ChatService;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class ChatController {
  private final ChatService service;

  @PostMapping("/chat")
  public List<String> sendMessage(@RequestBody String message) {
    return service.sendMessage(message);
  }
}
