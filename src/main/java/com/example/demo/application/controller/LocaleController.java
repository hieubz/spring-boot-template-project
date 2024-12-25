package com.example.demo.application.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Locale;

@RestController
@RequestMapping("/api/locale")
@RequiredArgsConstructor
public class LocaleController {

  private final MessageSource messageSource;

  @GetMapping("/greet")
  public String greet(Locale locale) {
    return messageSource.getMessage("greeting", null, locale);
  }
}
