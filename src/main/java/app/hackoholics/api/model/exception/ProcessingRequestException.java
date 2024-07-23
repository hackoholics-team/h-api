package app.hackoholics.api.model.exception;

import static app.hackoholics.api.model.exception.ApiException.ExceptionType.SERVER_EXCEPTION;

public final class ProcessingRequestException extends ApiException {
  public ProcessingRequestException(String message) {
    super(SERVER_EXCEPTION, message);
  }
}
