package app.hackoholics.api.unit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import app.hackoholics.api.endpoint.converter.PageConverter;
import app.hackoholics.api.model.dto.Page;
import app.hackoholics.api.model.exception.BadRequestException;
import org.junit.jupiter.api.Test;

class PageConverterTest {
  private final PageConverter subject = new PageConverter();

  @Test
  void convert_page_ok() {
    Page actual = subject.convert("1");

    assertNotNull(actual);
    assertEquals(1, actual.value());
  }

  @Test
  void convert_page_ko() {
    assertThrows(BadRequestException.class, () -> subject.convert("0"));
  }
}
