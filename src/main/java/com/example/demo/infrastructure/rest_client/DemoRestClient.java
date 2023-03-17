package com.example.demo.infrastructure.rest_client;

import com.example.demo.core.domain.Post;
import com.example.demo.infrastructure.config.feign.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient(
    name = "demo-rest-client",
    url = "${jsonplaceholder-api-host}",
    configuration = FeignConfig.class)
public interface DemoRestClient {

  @RequestMapping(method = RequestMethod.GET, value = "${jsonplaceholder-api-path}")
  List<Post> getPosts();

  /** Notes: can use @RequestHeader to add header into method params */
  @RequestMapping(
      method = RequestMethod.GET,
      value = "${jsonplaceholder-api-path}/{postId}",
      headers = {"${jsonplaceholder-api-token-header}=${jsonplaceholder-api-key}"},
      produces = "application/json")
  Post getPostById(@PathVariable("postId") Long postId);
}
