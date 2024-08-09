package app.hackoholics.api.endpoint.controller;

import static app.hackoholics.api.file.FileTyper.parseMediaTypeFromBytes;
import static java.util.UUID.randomUUID;

import app.hackoholics.api.endpoint.security.AuthProvider;
import app.hackoholics.api.service.FileService;
import app.hackoholics.api.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class FileController {
  private final FileService service;
  private final UserService userService;

  @PutMapping(value = "/files/{id}/raw")
  public String uploadFile(@PathVariable("id") String fId, @RequestBody byte[] file) {
    return service.uploadFile(fId, file);
  }

  @PutMapping(value = "/users/{id}/raw")
  public String updateUserProfile(@PathVariable("id") String uId, @RequestBody byte[] file) {
    var fileId = randomUUID().toString();
    var fileKey = service.uploadFile(fileId, file);
    var authUser = AuthProvider.getUser();
    authUser.setPhotoId(fileKey);
    userService.save(authUser);
    return fileKey;
  }

  @GetMapping(value = "/files/{id}/raw")
  public ResponseEntity<byte[]> downloadFile(@PathVariable("id") String fId) {
    var file = service.downloadFile(fId);
    return ResponseEntity.ok().contentType(parseMediaTypeFromBytes(file)).body(file);
  }
}
