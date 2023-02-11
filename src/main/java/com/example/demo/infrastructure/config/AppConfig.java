package com.example.demo.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.support.RetryTemplate;

@Configuration
public class AppConfig {

  @Bean
  public RetryTemplate retryTemplate() {
    RetryTemplate retryTemplate = new RetryTemplate();
    retryTemplate.registerListener(new CustomRetryListener());
    return retryTemplate;
  }
}
