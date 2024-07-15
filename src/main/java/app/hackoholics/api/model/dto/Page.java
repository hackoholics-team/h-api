package app.hackoholics.api.model.dto;

import app.hackoholics.api.model.exception.BadRequestException;

public record Page(int value) {
  public static final int MIN_PAGE = 1;

  public Page {
    if (value < MIN_PAGE) {
      throw new BadRequestException("page must be >=1");
    }
  }
}
