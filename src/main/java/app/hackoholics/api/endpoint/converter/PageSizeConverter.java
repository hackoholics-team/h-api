package app.hackoholics.api.endpoint.converter;

import app.hackoholics.api.model.dto.PageSize;
import org.springframework.core.convert.converter.Converter;

public class PageSizeConverter implements Converter<String, PageSize> {
  @Override
  public PageSize convert(String s) {
    return new PageSize(Integer.parseInt(s));
  }
}
