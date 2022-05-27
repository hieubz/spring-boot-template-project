package com.example.demo.application.controller;

import com.example.demo.application.response.PostResponse;
import com.example.demo.core.domain.Post;
import com.example.demo.core.service.PostService;
import com.example.demo.shared.exception.PostNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/posts")
@RequiredArgsConstructor
public class PostController extends BaseController {

  private final PostService postService;

  @GetMapping(value = "/get-last")
  public PostResponse getLastPost() throws PostNotFoundException {
    Post post = postService.getLastPost();
    return PostResponse.builder().post(post).status(true).build();
  }
}
