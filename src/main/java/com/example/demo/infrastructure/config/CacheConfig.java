package com.example.demo.infrastructure.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;

import java.time.Duration;

@Configuration
public class CacheConfig {

    private final int CACHE_REDIS_TTL = 60;

  @Bean
  @Primary
  public RedisCacheConfiguration defaultCacheConfig(ObjectMapper objectMapper) {
    return RedisCacheConfiguration.defaultCacheConfig()
        .entryTtl(Duration.ofSeconds(CACHE_REDIS_TTL))
        .disableCachingNullValues()
        .serializeValuesWith(
            RedisSerializationContext.SerializationPair.fromSerializer(
                new GenericJackson2JsonRedisSerializer(objectMapper)));
  }
}
