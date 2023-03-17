package com.example.demo.infrastructure.config.feign;

import com.example.demo.shared.constants.AppConstants;
import feign.RequestInterceptor;
import org.slf4j.MDC;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

public class FeignConfig {

  /** configure logging for specific profiles (stg, prod) */
  @Bean
  @Profile("!dev")
  public feign.Logger logger() {
    return new DemoFeignLogger();
  }

  /**
   * The interceptors can perform a variety of implicit tasks, from authentication to logging, for
   * every HTTP request/response. Ex: Here I added request id into header of every request for
   * tracing between services
   */
  @Bean
  public RequestInterceptor demoRequestInterceptor() {
    return template -> template.header(AppConstants.REQUEST_ID_KEY, MDC.get(AppConstants.REQUEST_ID_KEY));
  }
}
