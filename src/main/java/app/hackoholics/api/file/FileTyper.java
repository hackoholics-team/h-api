package app.hackoholics.api.file;

import lombok.SneakyThrows;
import org.apache.tika.Tika;

import java.io.File;
import java.util.function.Function;

import static org.springframework.http.MediaType.parseMediaType;

public class FileTyper implements Function<File, String> {
  @SneakyThrows
  @Override
  public String apply(File file) {
    var tika = new Tika();
    String detectedMediaType = tika.detect(file);
    return parseMediaType(detectedMediaType).toString();
  }
}
