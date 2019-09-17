package io.futurify.authentication.controller.handle;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import io.futurify.authentication.controller.exception.AuthorizeException;
import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {

  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(
      MethodArgumentNotValidException exception, HttpHeaders headers, HttpStatus status,
      WebRequest request) {
    List<String> errorDescriptions = new ArrayList<>();
    for (FieldError fieldError : exception.getBindingResult().getFieldErrors()) {
      errorDescriptions.add(fieldError.getDefaultMessage());
    }
    Map<String, Object> response = new LinkedHashMap<>();
    response.put("errorDescription", errorDescriptions);
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
  }

  @Override
  protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
      HttpHeaders headers, HttpStatus status, WebRequest request) {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("The export request is not readable");
  }
  
  @ExceptionHandler(value = AuthorizeException.class)
  public ResponseEntity<Map<String, Object>> handleJobException(AuthorizeException exception) {
    log.error("Authentication exception.", exception);
    Map<String, Object> response = new LinkedHashMap<>();
    response.put("errorDescription", exception.getMessage());
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
  }

}
