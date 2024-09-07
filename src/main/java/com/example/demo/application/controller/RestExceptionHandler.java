package com.example.demo.application.controller;

import com.example.demo.application.response.BaseResponse;
import com.example.demo.application.response.FindProductResponse;
import com.example.demo.application.response.PostResponse;
import com.example.demo.application.response.PriceCheckResponse;
import com.example.demo.shared.exception.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import org.springframework.validation.FieldError;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
@Slf4j
public class RestExceptionHandler {

  private static final String MSG_PRODUCT_NOT_FOUND = "Your product is not found";
  private static final String MSG_EMPTY_REQUEST = "Your request body is empty";
  private static final String MSG_POST_NOT_FOUND = "No posts found";

  @ExceptionHandler(value = ProductNotFoundException.class)
  public ResponseEntity<FindProductResponse> handleProductNotFoundException() {
    return new ResponseEntity<>(
        FindProductResponse.builder().message(MSG_PRODUCT_NOT_FOUND).success(false).build(),
        HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(value = EmptyRequestException.class)
  public ResponseEntity<PriceCheckResponse> handleEmptyRequestException() {
    return new ResponseEntity<>(
        PriceCheckResponse.builder().message(MSG_EMPTY_REQUEST).success(false).build(),
        HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(value = PostNotFoundException.class)
  public ResponseEntity<PostResponse> handlePostNotFoundException() {
    return new ResponseEntity<>(
        PostResponse.builder().message(MSG_POST_NOT_FOUND).success(false).build(), HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(value = ServiceUnavailableException.class)
  public ResponseEntity<String> handleServiceUnavailableException() {
    return new ResponseEntity<>(
        "Service is currently unavailable. Please try again later.", HttpStatus.SERVICE_UNAVAILABLE);
  }

  @ExceptionHandler(value = RateLimitExceededException.class)
  public ResponseEntity<String> handleRateLimitExceededException() {
    return new ResponseEntity<>(
        "Rate limit exceeded. Please try again later.", HttpStatus.TOO_MANY_REQUESTS);
  }

  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
    Map<String, String> errors = new HashMap<>();
    ex.getBindingResult().getAllErrors().forEach((error) -> {
      String fieldName = ((FieldError) error).getField();
      String errorMessage = error.getDefaultMessage();
      errors.put(fieldName, errorMessage);
    });
    return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(value = Exception.class)
  public ResponseEntity<BaseResponse> handleUnknownException(Exception exception) {
    log.error("> ERROR ", exception);
    return new ResponseEntity<>(
        BaseResponse.builder().message("Internal Server Error").success(false).build(),
        HttpStatus.INTERNAL_SERVER_ERROR);
  }
}
