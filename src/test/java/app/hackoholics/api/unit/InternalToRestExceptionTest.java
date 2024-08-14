package app.hackoholics.api.unit;

import static org.junit.jupiter.api.Assertions.assertEquals;

import app.hackoholics.api.endpoint.InternalToRestException;
import app.hackoholics.api.endpoint.rest.model.Exception;
import app.hackoholics.api.model.exception.BadRequestException;
import app.hackoholics.api.model.exception.ForbiddenException;
import app.hackoholics.api.model.exception.NotFoundException;
import app.hackoholics.api.model.exception.NotImplementedException;
import app.hackoholics.api.model.exception.ProcessingRequestException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class InternalToRestExceptionTest {

  @InjectMocks private InternalToRestException internalToRestException;
  private BadRequestException badRequestException;
  private ForbiddenException forbiddenException;
  private NotFoundException notFoundException;
  private ProcessingRequestException processingRequestException;
  private NotImplementedException notImplementedException;

  @BeforeEach
  public void setUp() {
    internalToRestException = new InternalToRestException();
    badRequestException = new BadRequestException("Bad request");
    forbiddenException = new ForbiddenException("Forbidden");
    notFoundException = new NotFoundException("Not found");
    processingRequestException = new ProcessingRequestException("Processing error");
    notImplementedException = new NotImplementedException("Not implemented");
  }

  @Test
  public void testHandleBadRequest() {
    ResponseEntity<Exception> response =
        internalToRestException.handleBadRequest(badRequestException);
    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    assertEquals("Bad request", response.getBody().getMessage());
  }

  @Test
  public void testHandleForbidden() {
    ResponseEntity<Exception> response =
        internalToRestException.handleForbidden(forbiddenException);
    assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
    assertEquals("Forbidden", response.getBody().getMessage());
  }

  @Test
  public void testHandleNotFound() {
    ResponseEntity<Exception> response = internalToRestException.handleNotFound(notFoundException);
    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    assertEquals("Not found", response.getBody().getMessage());
  }

  @Test
  public void testHandleNotImplemented() {
    ResponseEntity<Exception> response =
        internalToRestException.handleNotImplemented(notImplementedException);
    assertEquals(HttpStatus.NOT_IMPLEMENTED, response.getStatusCode());
    assertEquals("Not implemented", response.getBody().getMessage());
  }

  @Test
  public void testHandleProcessing() {
    ResponseEntity<Exception> response =
        internalToRestException.handleProcessing(processingRequestException);
    assertEquals(HttpStatus.PROCESSING, response.getStatusCode());
    assertEquals("Processing error", response.getBody().getMessage());
  }

  @Test
  public void testHandleDefault() {
    java.lang.Exception genericException = new java.lang.Exception("Generic error");
    ResponseEntity<Exception> response = internalToRestException.handleDefault(genericException);
    assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    assertEquals("Generic error", response.getBody().getMessage());
  }
}
