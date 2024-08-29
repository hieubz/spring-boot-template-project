package com.example.demo.application.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.validation.constraints.Min;
import lombok.Data;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class UpdateProductRequest {

  @Min(value = 1, message = "The value must be greater than 0")
  private int id;

  private String name;

  @Min(value = 0, message = "The value must be greater than or equal 0")
  private Float price;
  private Integer categoryId;
}
