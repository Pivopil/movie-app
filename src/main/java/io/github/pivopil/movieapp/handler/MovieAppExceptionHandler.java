package io.github.pivopil.movieapp.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ValidationException;

@Slf4j
@ControllerAdvice
public class MovieAppExceptionHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler(value = {RuntimeException.class})
  protected ResponseEntity<Object> handleConflict(RuntimeException ex, WebRequest request) {
    HttpStatus status = HttpStatus.CONFLICT;
    String message = "Unhandled exception";
    if (ex instanceof ValidationException) {
      status = HttpStatus.BAD_REQUEST;
      message = ex.getMessage();
      log.error(message);
    } else {
      log.error(message, ex);
    }
    return handleExceptionInternal(ex, message, new HttpHeaders(), status, request);
  }
}
