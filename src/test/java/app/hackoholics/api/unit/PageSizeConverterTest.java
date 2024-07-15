package app.hackoholics.api.unit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import app.hackoholics.api.endpoint.converter.PageSizeConverter;
import app.hackoholics.api.model.dto.PageSize;
import app.hackoholics.api.model.exception.BadRequestException;
import org.junit.jupiter.api.Test;

class PageSizeConverterTest {
  private final PageSizeConverter subject = new PageSizeConverter();

  @Test
  void convert_page_size_ok() {
    PageSize actual = subject.convert("1");

    assertNotNull(actual);
    assertEquals(1, actual.value());
  }

  @Test
  void convert_page_size_ko() {
    assertThrows(BadRequestException.class, () -> subject.convert("0"));
    assertThrows(BadRequestException.class, () -> subject.convert("1000"));
  }
}
