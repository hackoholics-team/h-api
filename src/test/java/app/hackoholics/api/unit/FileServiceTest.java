package app.hackoholics.api.unit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import app.hackoholics.api.model.exception.ApiException;
import app.hackoholics.api.service.FileService;
import app.hackoholics.api.service.google.filestorage.BucketComponent;
import java.io.IOException;
import java.util.Arrays;
import org.junit.jupiter.api.Test;

class FileServiceTest {
  private static final String FILE_ID = "fileId";
  private final BucketComponent bucketComponent = mock();
  private final FileService subject = new FileService(bucketComponent);

  @Test
  void upload_file_ok() throws IOException {
    when(bucketComponent.uploadFile(any(), any())).thenReturn(FILE_ID);

    var actual = subject.uploadFile(FILE_ID, new byte[] {});

    assertEquals(FILE_ID, actual);
  }

  @Test
  void upload_file_ko() throws IOException {
    when(bucketComponent.uploadFile(any(), any())).thenThrow(new IOException());

    assertThrows(ApiException.class, () -> subject.uploadFile(FILE_ID, new byte[] {}));
  }

  @Test
  void download_file_ok() {
    when(bucketComponent.downloadFile(FILE_ID)).thenReturn(new byte[] {});

    var actual = subject.downloadFile(FILE_ID);

    assertEquals(Arrays.toString(new byte[] {}), Arrays.toString(actual));
  }
}
