package nectordiaz.clothesshop.pricing.adapters.in.web.exception;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.instancio.Instancio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

class ApiExceptionHandlerTest {

  private ApiExceptionHandler handler;

  @BeforeEach
  void setUp() {
    handler = new ApiExceptionHandler();
  }

  @Test
  void handleMissingParam_returnsBadRequestAndMessage() {
    String paramName = Instancio.create(String.class);
    String paramType = Instancio.create(String.class);

    MissingServletRequestParameterException ex =
        new MissingServletRequestParameterException(paramName, paramType);

    ResponseEntity<?> response = handler.handleMissingParam(ex);

    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    assertNotNull(response.getBody());
    String bodyMessage = extractMessage(response.getBody());
    assertEquals(
        String.format("ErrorResponse(error=Missing required parameter '%s')", paramName),
        bodyMessage);
  }

  @Test
  void handleTypeMismatch_returnsBadRequestAndIncludesDetails() {
    String paramName = Instancio.create(String.class);
    MethodArgumentTypeMismatchException ex = mock(MethodArgumentTypeMismatchException.class);

    when(ex.getName()).thenReturn(paramName);
    doReturn(Integer.class).when(ex).getRequiredType();
    when(ex.getValue()).thenReturn("abc");
    when(ex.getMessage()).thenReturn("type mismatch");

    ResponseEntity<?> response = handler.handleTypeMismatch(ex);

    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    assertNotNull(response.getBody());
    String bodyMessage = extractMessage(response.getBody());
    assertTrue(bodyMessage.contains("Invalid parameter"));
    assertTrue(bodyMessage.contains(paramName));
    assertTrue(bodyMessage.contains("Integer"));
  }

  @Test
  void handleNotFound_returnsNotFoundAndMessage() {
    String message = Instancio.create(String.class);
    NotFoundException ex = new NotFoundException(message);

    ResponseEntity<?> response = handler.handleNotFound(ex);

    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    assertNotNull(response.getBody());
    String bodyMessage = extractMessage(response.getBody());
    assertEquals(String.format("ErrorResponse(error=%s)", message), bodyMessage);
  }

  @Test
  void handleGeneric_returnsInternalServerError() {
    Exception ex = new Exception(Instancio.create(String.class));

    ResponseEntity<?> response = handler.handleGeneric(ex);

    assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    assertNotNull(response.getBody());
    String bodyMessage = extractMessage(response.getBody());
    assertEquals("ErrorResponse(error=Internal server error)", bodyMessage);
  }

  private String extractMessage(Object body) {
    if (body == null) {
      return null;
    }
    try {
      var method = body.getClass().getMethod("getMessage");
      Object value = method.invoke(body);
      return value != null ? value.toString() : null;
    } catch (Exception e) {
      return body.toString();
    }
  }
}
