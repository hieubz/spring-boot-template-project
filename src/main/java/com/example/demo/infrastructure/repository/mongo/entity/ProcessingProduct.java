package com.example.demo.infrastructure.repository.mongo.entity;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;

@Document(collection = "processing_products")
@Data
@Builder
public class ProcessingProduct {
  @Field("product_id")
  private Long productId;

  @CreatedDate private Date created;
}
