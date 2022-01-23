package com.example.demo.application.controller;

import com.example.demo.application.response.BaseResponse;
import com.example.demo.application.response.FindProductResponse;
import com.example.demo.infrastructure.exception.ProductNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler {

  private static final String MSG_PRODUCT_NOT_FOUND = "Your product is not found";

  @ExceptionHandler(value = ProductNotFoundException.class)
  public ResponseEntity<FindProductResponse> handleProductNotFoundException() {
    return new ResponseEntity<>(
        FindProductResponse.builder().msg(MSG_PRODUCT_NOT_FOUND).status(false).build(),
        HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(value = Exception.class)
  public ResponseEntity<BaseResponse> handleUnknownException() {
    return new ResponseEntity<>(
        BaseResponse.builder().msg("Internal Server Error").status(false).build(),
        HttpStatus.INTERNAL_SERVER_ERROR);
  }
}
