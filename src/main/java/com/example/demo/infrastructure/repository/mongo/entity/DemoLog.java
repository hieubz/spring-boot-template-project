package com.example.demo.infrastructure.repository.mongo.entity;

import com.example.demo.shared.constants.AppConstants;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;

@Document(collection = "demo_logs")
@Data
@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class DemoLog {

  @Field("request_id")
  @Indexed(name = "request_id_index")
  private String requestId;

  @CreatedDate
  @Indexed(name = "created_index", expireAfter = AppConstants.DEMO_LOG_TTL)
  private Date created;
}
