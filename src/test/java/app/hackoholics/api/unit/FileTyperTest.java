package app.hackoholics.api.unit;

import static org.junit.jupiter.api.Assertions.assertEquals;

import app.hackoholics.api.file.FileTyper;
import java.io.File;
import java.nio.file.Files;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.http.MediaType;

public class FileTyperTest {

  @InjectMocks private FileTyper fileTyper;

  @BeforeEach
  public void setUp() {
    fileTyper = new FileTyper();
  }

  @Test
  public void test_apply_with_valid_file() throws Exception {
    File tempFile = File.createTempFile("test", ".txt");
    Files.writeString(tempFile.toPath(), "This is a test file.");

    String mediaType = fileTyper.apply(tempFile);

    assertEquals("text/plain", mediaType);

    tempFile.delete();
  }

  @Test
  public void test_apply_with_image_file() throws Exception {
    File tempFile = File.createTempFile("test", ".png");
    byte[] imageBytes =
        new byte[] {
          (byte) 137, 80, 78, 71, 13, 10, 26, 10, 0, 0, 0, 13, 73, 72, 68, 82, 0, 0, 0, 1, 0, 0, 0,
          1, 8, 6, 0, 0, 0, 31, -120, -119, 88, 0, 0, 0, 12, 73, 68, 65, 84, 8, -29, 99, 96, 0, 0,
          0, 2, 0, 1, 46, -71, 4, 10, 0, 0, 0, 0, 73, 69, 78, 68, -82, 66, 96, -126
        };
    Files.write(tempFile.toPath(), imageBytes);

    String mediaType = fileTyper.apply(tempFile);

    assertEquals("image/png", mediaType);

    tempFile.delete();
  }

  @Test
  public void test_parse_media_type_from_bytes() {
    byte[] imageBytes =
        new byte[] {
          (byte) 137, 80, 78, 71, 13, 10, 26, 10, 0, 0, 0, 13, 73, 72, 68, 82, 0, 0, 0, 1, 0, 0, 0,
          1, 8, 6, 0, 0, 0, 31, -120, -119, 88, 0, 0, 0, 12, 73, 68, 65, 84, 8, -29, 99, 96, 0, 0,
          0, 2, 0, 1, 46, -71, 4, 10, 0, 0, 0, 0, 73, 69, 78, 68, -82, 66, 96, -126
        };

    MediaType mediaType = FileTyper.parseMediaTypeFromBytes(imageBytes);

    assertEquals(MediaType.IMAGE_PNG, mediaType);
  }
}
