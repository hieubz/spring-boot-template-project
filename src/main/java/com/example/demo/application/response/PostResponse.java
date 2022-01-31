package com.example.demo.application.response;

import com.example.demo.core.domain.Post;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PostResponse extends BaseResponse {
  private Post post;
}
