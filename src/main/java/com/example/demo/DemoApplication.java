package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.elasticsearch.ElasticsearchRepositoriesAutoConfiguration;
import org.springframework.boot.autoconfigure.elasticsearch.ElasticsearchClientAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication(exclude = {ElasticsearchClientAutoConfiguration.class, ElasticsearchRepositoriesAutoConfiguration.class})
@EnableCaching
@EnableAsync
@EnableFeignClients
@EnableRetry
public class DemoApplication {

  public static void main(String[] args) {
    SpringApplication.run(DemoApplication.class, args);
  }
}
