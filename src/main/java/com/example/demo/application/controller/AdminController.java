package com.example.demo.application.controller;

import com.example.demo.application.response.BaseResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin")
public class AdminController extends BaseController {

  @GetMapping
  public BaseResponse getAdmin() {
    return BaseResponse.builder().message("This is the admin site").success(true).build();
  }
}
