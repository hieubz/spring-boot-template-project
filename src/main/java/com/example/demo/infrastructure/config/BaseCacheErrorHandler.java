package com.example.demo.infrastructure.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.Cache;
import org.springframework.cache.interceptor.CacheErrorHandler;

@Slf4j
public class BaseCacheErrorHandler implements CacheErrorHandler {

  @Override
  public void handleCacheGetError(RuntimeException exception, Cache cache, Object key) {
    handleCacheException(exception);
    log.error("Unable to get from cache " + cache.getName(), exception);
  }

  @Override
  public void handleCachePutError(
      RuntimeException exception, Cache cache, Object key, Object value) {
    handleCacheException(exception);
    log.error("Unable to put into cache " + cache.getName(), exception);
  }

  @Override
  public void handleCacheEvictError(RuntimeException exception, Cache cache, Object key) {
    handleCacheException(exception);
    log.error("Unable to evict from cache " + cache.getName(), exception);
  }

  @Override
  public void handleCacheClearError(RuntimeException exception, Cache cache) {
    handleCacheException(exception);
    log.error("Unable to clear cache " + cache.getName(), exception);
  }

  /**
   * if the exception is handled then it is treated as a cache miss and
   * gets the data from actual storage
   */
  private void handleCacheException(RuntimeException exception) {
    // do nothing.
  }
}
