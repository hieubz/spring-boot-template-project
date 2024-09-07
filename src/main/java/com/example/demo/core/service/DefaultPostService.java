package com.example.demo.core.service;

import com.example.demo.core.domain.Post;
import com.example.demo.infrastructure.msg_queue.activemq.ActiveMQProducer;
import com.example.demo.infrastructure.msg_queue.vo.DemoMessage;
import com.example.demo.infrastructure.rest_client.DemoRestClient;
import com.example.demo.shared.exception.PostNotFoundException;
import com.example.demo.shared.exception.ServiceUnavailableException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;

@Component("PostService")
@RequiredArgsConstructor
@Slf4j
public class DefaultPostService implements PostService {

  private final DemoRestClient demoRestClient;
  private final ActiveMQProducer activeMQProducer;

  @Override
  @Retryable(
      retryFor = PostNotFoundException.class, // for testing purpose, we should not retry if not found =))
      maxAttemptsExpression = "${retry.maxAttempts}",
      backoff = @Backoff(delayExpression = "${retry.backOffDelay}"))
  @CircuitBreaker(name = "demo-feign-client", fallbackMethod = "getLastPostCircuitFallback")
  public Post getLastPost() throws PostNotFoundException {
    Post post = demoRestClient.getPostById(100L); // just for demo =)))
    if (post == null) {
      log.info("No Posts Found");
      throw new PostNotFoundException();
    }
    return post;
  }

  /**
   * We should declare the fallback at Service layer to maintain better separation of concerns.
   */
  public Post getLastPostCircuitFallback(Exception e) throws Exception {
    // for each exception, we can have a diff scenario
    if (e instanceof PostNotFoundException) throw e;
    log.error("Post Service is unavailable", e);
    throw new ServiceUnavailableException("Post Service is unavailable");
  }

  @Override
  public void testActiveMQ(String queueName) {
    try {
      activeMQProducer.sendMessage(
          queueName, DemoMessage.builder().id(10L).created(LocalDateTime.now()).build());
    } catch (Exception e) {
      log.error("> ERROR failure in sending message to {}", queueName);
    }
  }
}
