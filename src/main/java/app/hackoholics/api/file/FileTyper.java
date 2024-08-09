package app.hackoholics.api.file;

import static org.springframework.http.MediaType.parseMediaType;

import java.io.File;
import java.util.function.Function;
import lombok.SneakyThrows;
import org.apache.tika.Tika;
import org.springframework.http.MediaType;

public class FileTyper implements Function<File, String> {
  @SneakyThrows
  @Override
  public String apply(File file) {
    var tika = new Tika();
    String detectedMediaType = tika.detect(file);
    return parseMediaType(detectedMediaType).toString();
  }

  public static MediaType parseMediaTypeFromBytes(byte[] bytes) {
    Tika tika = new Tika();
    String guessedMediaTypeValue = tika.detect(bytes);
    return MediaType.parseMediaType(guessedMediaTypeValue);
  }
}
