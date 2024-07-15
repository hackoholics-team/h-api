package app.hackoholics.api.model.dto;

import app.hackoholics.api.model.exception.BadRequestException;
import lombok.Getter;

public class Page {
  public static final int MIN_PAGE = 1;
  @Getter private final int value;

  public Page(int value) {
    if (value < MIN_PAGE) {
      throw new BadRequestException("page must be >=1");
    }
    this.value = value;
  }
}
