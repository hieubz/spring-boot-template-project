package com.example.demo.application.controller;

import com.example.demo.application.response.BaseResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/db")
public class DatabaseController {

  @GetMapping
  public BaseResponse getDBAdmin() {
    return BaseResponse.builder().message("This is the database admin site").success(true).build();
  }
}
