package com.example.demo.infrastructure.rest_client;

import com.example.demo.core.domain.Post;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient(name = "demo-rest-client", url = "https://jsonplaceholder.typicode.com/")
public interface DemoRestClient {

  @RequestMapping(method = RequestMethod.GET, value = "/posts")
  List<Post> getPosts();
}
