package com.example.demo.core.domain;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class Order {
  private Integer id;
  private Integer userId;
  private LocalDateTime created;
}
