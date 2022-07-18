package com.example.demo.infrastructure.msg_queue.vo;

import com.example.demo.infrastructure.msg_queue.Message;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class DemoMessage extends Message {
  private Long id;
  private LocalDateTime created;
}
