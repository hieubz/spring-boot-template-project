package com.example.demo.core.service;

import com.example.demo.core.domain.Post;
import com.example.demo.shared.exception.PostNotFoundException;

public interface PostService {

  Post getLastPost() throws PostNotFoundException;

  void testActiveMQ(String queueName);
}
