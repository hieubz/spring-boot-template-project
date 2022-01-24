package com.example.demo.infrastructure.exception;

public class EmptyRequestException extends BaseException {

  public EmptyRequestException(String msg) {
    super(msg);
  }

  public EmptyRequestException() {
    super();
  }
}
