package com.example.demo.infrastructure.msg_queue;

import org.springframework.stereotype.Component;

@Component
public class MyCustomDltProcessor {

  public void processDltMessage(String message) {
    // ... message processing, persistence, etc
  }
}
