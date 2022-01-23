package com.example.demo.application.response;

import com.example.demo.core.domain.Product;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FindProductResponse extends BaseResponse {
  private Product product;
}
