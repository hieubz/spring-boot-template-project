package com.example.demo.application.controller;

import com.example.demo.application.response.PostResponse;
import com.example.demo.core.domain.Post;
import com.example.demo.core.service.PostService;
import com.example.demo.shared.constants.AppConstants;
import com.example.demo.shared.exception.PostNotFoundException;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/posts")
@Tag(name = "Post Controller")
@RequiredArgsConstructor
public class PostController extends BaseController {

  private final PostService postService;

  @GetMapping(value = "/get-last")
  @Parameter(
      name = AppConstants.FIXED_TOKEN_HEADER,
      required = true,
      in = ParameterIn.HEADER,
      example = "39489c18-7b74-11ec-90d6-0242ac120003")
  public PostResponse getLastPost() throws PostNotFoundException {
    Post post = postService.getLastPost();
    return PostResponse.builder().post(post).success(true).build();
  }

  @GetMapping(value = "/test-activemq")
  @Parameter(
          name = AppConstants.FIXED_TOKEN_HEADER,
          required = true,
          in = ParameterIn.HEADER,
          example = "39489c18-7b74-11ec-90d6-0242ac120003")
  public PostResponse testActiveMQ() {
    postService.testActiveMQ("demo-queue");
    return PostResponse.builder().success(true).build();
  }
}
