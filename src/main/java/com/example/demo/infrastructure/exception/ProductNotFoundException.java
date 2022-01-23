package com.example.demo.infrastructure.exception;

public class ProductNotFoundException extends BaseException {

  public ProductNotFoundException(String msg) {
    super(msg);
  }
}
