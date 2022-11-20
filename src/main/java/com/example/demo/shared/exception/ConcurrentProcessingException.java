package com.example.demo.shared.exception;

public class ConcurrentProcessingException extends BaseException {

  public ConcurrentProcessingException() {}

  public ConcurrentProcessingException(String msg) {
    super(msg);
  }
}
