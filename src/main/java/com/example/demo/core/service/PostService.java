package com.example.demo.core.service;

import com.example.demo.core.domain.Post;
import com.example.demo.shared.exception.PostNotFoundException;
import com.example.demo.infrastructure.rest_client.DemoRestClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("PostService")
@RequiredArgsConstructor
@Slf4j
public class PostService {

  private final DemoRestClient demoRestClient;

  @Retryable(
      value = PostNotFoundException.class,
      maxAttemptsExpression = "${retry.maxAttempts}",
      backoff = @Backoff(delayExpression = "${retry.backOffDelay}"))
  public Post getLastPost() throws PostNotFoundException {
    List<Post> posts = demoRestClient.getPosts();
    if (posts.isEmpty()) {
      log.error("No Posts Found");
      throw new PostNotFoundException();
    }
    return posts.get(0);
  }
}
