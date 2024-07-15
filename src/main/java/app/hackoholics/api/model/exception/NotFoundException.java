package app.hackoholics.api.model.exception;

import static app.hackoholics.api.model.exception.ApiException.ExceptionType.CLIENT_EXCEPTION;

public final class NotFoundException extends ApiException {
  public NotFoundException(String message) {
    super(CLIENT_EXCEPTION, message);
  }
}
