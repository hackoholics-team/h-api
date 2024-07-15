package app.hackoholics.api.model.dto;

import app.hackoholics.api.model.exception.BadRequestException;

public record PageSize(int value) {
  public static final int MAX_SIZE = 500;

  public PageSize {
    if (value < 1) {
      throw new BadRequestException("page size must be >=1");
    }
    if (value > MAX_SIZE) {
      throw new BadRequestException("page size must be <" + MAX_SIZE);
    }
  }
}
