package app.hackoholics.api.unit;

import static java.util.UUID.randomUUID;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import app.hackoholics.api.file.TemplateHandler;
import app.hackoholics.api.model.User;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import org.junit.jupiter.api.Test;

class TemplateHandlerTest {
  private final TemplateHandler<User> subject = new TemplateHandler<>();

  User user() {
    return User.builder().username("John Doe").build();
  }

  void generatePdf() throws IOException {
    var actual = subject.generatePdf(user(), "document");
    File generatedFile = new File(randomUUID() + ".pdf");
    OutputStream os = new FileOutputStream(generatedFile);
    os.write(actual);
    generatedFile.delete();
    os.close();
  }

  @Test
  void generate_invoice_pdf_ok() {
    assertDoesNotThrow(this::generatePdf);
  }
}
