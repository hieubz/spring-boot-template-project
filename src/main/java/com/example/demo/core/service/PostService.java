package com.example.demo.core.service;

import com.example.demo.core.domain.Post;
import com.example.demo.infrastructure.rest_client.DemoRestClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("PostService")
@RequiredArgsConstructor
public class PostService {

  private final DemoRestClient demoRestClient;

  public Post getLastPost() {
    List<Post> posts = demoRestClient.getPosts();
    if (posts.isEmpty()) return new Post();
    return posts.get(0);
  }
}
