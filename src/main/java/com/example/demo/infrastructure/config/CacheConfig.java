package com.example.demo.infrastructure.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurer;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.cache.interceptor.CacheErrorHandler;
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
import java.util.concurrent.TimeUnit;

@Configuration
public class CacheConfig extends CachingConfigurerSupport implements CachingConfigurer {

  public static final String CACHE_REDIS = "CACHE_REDIS";
  public static final String CACHE_LOCAL = "CACHE_LOCAL";
  private final int CACHE_REDIS_TTL = 60;
  private final int CACHE_LOCAL_TTL = 120;

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

  @Bean(name = CACHE_LOCAL)
  public CacheManager localCacheManager() {
    CaffeineCacheManager caffeineCacheManager = new CaffeineCacheManager();
    caffeineCacheManager.setCaffeine(
        Caffeine.newBuilder()
            .initialCapacity(50)
            .maximumSize(3000)
            .expireAfterWrite(CACHE_LOCAL_TTL, TimeUnit.SECONDS));
    return caffeineCacheManager;
  }

  @Override
  public CacheErrorHandler errorHandler() {
    return new BaseCacheErrorHandler();
  }
}
