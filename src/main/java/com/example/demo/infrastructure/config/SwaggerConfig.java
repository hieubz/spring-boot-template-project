package com.example.demo.infrastructure.config;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

  @Bean
  public GroupedOpenApi publicApi() {
    return GroupedOpenApi.builder()
        .packagesToScan("com.example.demo.application")
        .group("demo-app")
        .build();
  }
}
