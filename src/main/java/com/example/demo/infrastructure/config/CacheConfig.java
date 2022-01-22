package com.example.demo.infrastructure.config;

import org.springframework.cache.annotation.CachingConfigurer;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;

import java.time.Duration;

@Configuration
public class CacheConfig extends CachingConfigurerSupport implements CachingConfigurer {

  public static final String CACHE_REDIS = "CACHE_REDIS";
  private final int CACHE_REDIS_TTL = 60;

  public RedisCacheConfiguration buildCacheConfig(int seconds) {
    return RedisCacheConfiguration.defaultCacheConfig()
        .entryTtl(Duration.ofSeconds(seconds))
        .disableCachingNullValues()
        .serializeValuesWith(
            RedisSerializationContext.SerializationPair.fromSerializer(
                new GenericJackson2JsonRedisSerializer()));
  }

  @Bean
  public RedisCacheWriter redisCacheWriter(RedisConnectionFactory redisConnectionFactory) {
    return RedisCacheWriter.nonLockingRedisCacheWriter(redisConnectionFactory);
  }

  @Bean(name = CACHE_REDIS)
  @Primary
  public RedisCacheManager redisCacheManager(RedisCacheWriter redisCacheWriter) {
    return new RedisCacheManager(redisCacheWriter, buildCacheConfig(CACHE_REDIS_TTL));
  }
}
