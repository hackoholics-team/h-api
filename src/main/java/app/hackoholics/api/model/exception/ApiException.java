package app.hackoholics.api.model.exception;

public sealed class ApiException extends RuntimeException
    permits BadRequestException,
        ForbiddenException,
        NotFoundException,
        NotImplementedException,
        ProcessingRequestException {
  private final ExceptionType type;

  public ApiException(ExceptionType type, String message) {
    super(message);
    this.type = type;
  }

  public enum ExceptionType {
    SERVER_EXCEPTION,
    CLIENT_EXCEPTION
  }
}
