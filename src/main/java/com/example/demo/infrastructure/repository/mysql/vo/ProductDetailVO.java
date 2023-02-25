package com.example.demo.infrastructure.repository.mysql.vo;

import java.time.LocalDateTime;

public interface ProductDetailVO {

  Integer getId();

  String getName();

  Float getPrice();

  Integer getQuantity();

  String getImagePath();

  LocalDateTime getCreated();
}
