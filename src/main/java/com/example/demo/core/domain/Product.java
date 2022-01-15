package com.example.demo.core.domain;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class Product {
  private Long id;
  private String name;
  private Float price;
  private String author;
  private Integer number;
  private String imagePath;
}
