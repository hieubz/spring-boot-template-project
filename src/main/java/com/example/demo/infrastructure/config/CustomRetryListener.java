package com.example.demo.infrastructure.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.retry.RetryCallback;
import org.springframework.retry.RetryContext;
import org.springframework.retry.RetryListener;
import org.springframework.stereotype.Component;

@Component("CustomRetryListener")
@Slf4j
public class CustomRetryListener implements RetryListener {

  @Override
  public <T, E extends Throwable> boolean open(
      RetryContext retryContext, RetryCallback<T, E> retryCallback) {
    return true;
  }

  @Override
  public <T, E extends Throwable> void close(
      RetryContext retryContext, RetryCallback<T, E> retryCallback, Throwable throwable) {}

  @Override
  public <T, E extends Throwable> void onError(
      RetryContext retryContext, RetryCallback<T, E> retryCallback, Throwable throwable) {
    log.warn("onError {}, retry count: {}", throwable.getMessage(), retryContext.getRetryCount());
  }
}
