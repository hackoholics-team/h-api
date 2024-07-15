package app.hackoholics.api.model.exception;

import static app.hackoholics.api.model.exception.ApiException.ExceptionType.SERVER_EXCEPTION;

public final class NotImplementedException extends ApiException {
  public NotImplementedException(String message) {
    super(SERVER_EXCEPTION, message);
  }
}
