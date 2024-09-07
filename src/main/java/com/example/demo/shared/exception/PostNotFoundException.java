package com.example.demo.shared.exception;

public class PostNotFoundException extends RuntimeException {

  public PostNotFoundException(String msg) {
    super(msg);
  }

  public PostNotFoundException() {
    super();
  }
}
