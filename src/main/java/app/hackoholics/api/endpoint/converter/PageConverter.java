package app.hackoholics.api.endpoint.converter;

import app.hackoholics.api.model.dto.Page;
import org.springframework.core.convert.converter.Converter;

public class PageConverter implements Converter<String, Page> {
  @Override
  public Page convert(String s) {
    return new Page(Integer.parseInt(s));
  }
}
