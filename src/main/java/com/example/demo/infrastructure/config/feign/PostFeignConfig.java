package com.example.demo.infrastructure.config.feign;

import com.example.demo.shared.exception.PostNotFoundException;
import feign.codec.ErrorDecoder;
import io.undertow.util.BadRequestException;
import org.springframework.context.annotation.Bean;

public class PostFeignConfig extends FeignConfig {

  /**
   * Intercepts Feign client errors and translates them into our custom exceptions.
   * When an error occurs, the Feign client suppresses the original message.
   * To retrieve it, we can write a custom ErrorDecoder.
   */
  @Bean
  public ErrorDecoder postErrorDecoder() {
    return (methodKey, response) ->
        switch (response.status()) {
          case 404 -> new PostNotFoundException("Post not found");
          case 400 -> new BadRequestException("Bad Request");
          default -> new ErrorDecoder.Default().decode(methodKey, response);
        };
  }
}
