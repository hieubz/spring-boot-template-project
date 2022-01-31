package com.example.demo.infrastructure.exception;

public class PostNotFoundException extends BaseException {

  public PostNotFoundException(String msg) {
    super(msg);
  }

  public PostNotFoundException() {
    super();
  }
}
