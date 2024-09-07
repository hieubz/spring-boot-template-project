package com.example.demo.shared.exception;

public class ServiceUnavailableException extends RuntimeException {

  public ServiceUnavailableException(String msg) {
    super(msg);
  }

  public ServiceUnavailableException() {
    super();
  }
}
