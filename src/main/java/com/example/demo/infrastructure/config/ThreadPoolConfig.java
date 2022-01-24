package com.example.demo.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.net.InetAddress;
import java.net.UnknownHostException;

@Configuration
public class ThreadPoolConfig {

  @Bean("threadPoolTaskExecutor")
  public TaskExecutor getAsyncTaskExecutor() throws UnknownHostException {
    return buildPoolTaskExecutor("Demo-Async-");
  }

  @Bean("threadPoolTaskExecutor2")
  public TaskExecutor getAsyncTaskExecutor2() throws UnknownHostException {
    return buildPoolTaskExecutor("Demo-Async2-");
  }

  /**
   * Create a pool with 5 threads.
   * When queue is full with 200 pending tasks => JVM creates new thread up to maxPoolSize.
   * Once all threads (maxPoolSize) are running and if new task is arriving then that new task will be rejected
   */
  public TaskExecutor buildPoolTaskExecutor(String prefix) throws UnknownHostException {
    ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();

    InetAddress localhost = InetAddress.getLocalHost();
    int maxPoolSize = Runtime.getRuntime().availableProcessors() * 8;
    executor.setCorePoolSize(10);
    executor.setQueueCapacity(200);
    executor.setMaxPoolSize(maxPoolSize);
    executor.setWaitForTasksToCompleteOnShutdown(true);
    executor.setAllowCoreThreadTimeOut(false);
    executor.setThreadNamePrefix(prefix + localhost.getHostName() + "-");
    return executor;
  }
}
