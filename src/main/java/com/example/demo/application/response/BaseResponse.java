package com.example.demo.application.response;

import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
public class BaseResponse {
  protected String message;
  protected Boolean success;
}
