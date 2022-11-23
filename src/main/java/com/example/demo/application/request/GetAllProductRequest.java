package com.example.demo.application.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class GetAllProductRequest {
  private List<Integer> categoryIds;
  private Long minPrice;
  private Long maxPrice;
  private Boolean status;
  private Integer limit;
  private Integer page;
}
