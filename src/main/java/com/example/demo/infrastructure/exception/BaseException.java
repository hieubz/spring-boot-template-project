package com.example.demo.infrastructure.exception;

public class BaseException extends Exception {

  public BaseException(String msg) {
    super(msg);
  }

  public BaseException() {
    super();
  }
}
