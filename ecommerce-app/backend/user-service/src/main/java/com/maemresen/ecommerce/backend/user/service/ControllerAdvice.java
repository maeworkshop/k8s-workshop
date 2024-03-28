package com.maemresen.ecommerce.backend.user.service;

import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ControllerAdvice {

  @ExceptionHandler(Exception.class)
  public ResponseEntity<Map<String, String>> handleError(final Exception exception) {
    log.error("Failed to process request.", exception);
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<Map<String, String>> handleError(final MethodArgumentNotValidException exception) {
    log.error("Failed to process request.", exception);
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
  }
}
