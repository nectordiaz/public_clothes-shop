package nectordiaz.clothesshop.pricing.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@ControllerAdvice
public class ApiExceptionHandler {

  private static final Logger logger = LoggerFactory.getLogger(ApiExceptionHandler.class);

  @ExceptionHandler(MissingServletRequestParameterException.class)
  public ResponseEntity<?> handleMissingParam(MissingServletRequestParameterException ex) {
    String name = ex.getParameterName();
    String msg = String.format("Missing required parameter '%s'", name);
    logger.warn("{} - exception: {}", msg, ex.getMessage(), ex);
    return ResponseEntity.badRequest().body(new ErrorResponse(msg));
  }

  @ExceptionHandler(MethodArgumentTypeMismatchException.class)
  public ResponseEntity<?> handleTypeMismatch(MethodArgumentTypeMismatchException ex) {
    String param = ex.getName();
    String expected =
        ex.getRequiredType() != null ? ex.getRequiredType().getSimpleName() : "required type";
    String msg = String.format("Invalid parameter '%s', expected type %s", param, expected);
    logger.warn(
        "Type mismatch detected for parameter '{}': expected={} value={} - {}",
        param,
        expected,
        ex.getValue(),
        ex.getMessage(),
        ex);
    return ResponseEntity.badRequest().body(new ErrorResponse(msg));
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<?> handleGeneric(Exception ex) {
    // Aquí habría que meter algún mecanismo o sistema de alertas para categorizar qué excepciones
    // de qué casos de uso levantar como críticas
    logger.error("Unhandled exception caught in ApiExceptionHandler: {}", ex.getMessage(), ex);
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body(new ErrorResponse("Internal server error"));
  }

  @Data
  @AllArgsConstructor
  static class ErrorResponse {

    private String error;
  }
}
