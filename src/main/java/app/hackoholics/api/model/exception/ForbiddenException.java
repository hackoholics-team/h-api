package app.hackoholics.api.model.exception;

import static app.hackoholics.api.model.exception.ApiException.ExceptionType.CLIENT_EXCEPTION;

public final class ForbiddenException extends ApiException {
  public ForbiddenException(String message) {
    super(CLIENT_EXCEPTION, message);
  }
}
