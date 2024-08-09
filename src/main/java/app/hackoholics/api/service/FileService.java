package app.hackoholics.api.service;

import static app.hackoholics.api.model.exception.ApiException.ExceptionType.SERVER_EXCEPTION;

import app.hackoholics.api.model.exception.ApiException;
import app.hackoholics.api.service.google.filestorage.BucketComponent;
import java.io.IOException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class FileService {
  private final BucketComponent bucketComponent;

  public String uploadFile(String fileKey, byte[] file) {
    try {
      return bucketComponent.uploadFile(fileKey, file);
    } catch (IOException e) {
      throw new ApiException(SERVER_EXCEPTION, e.getMessage());
    }
  }

  public byte[] downloadFile(String fileKey) {
    return bucketComponent.downloadFile(fileKey);
  }
}
