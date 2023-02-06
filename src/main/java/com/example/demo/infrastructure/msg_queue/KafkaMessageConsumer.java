package com.example.demo.infrastructure.msg_queue;

import com.example.demo.infrastructure.msg_queue.vo.DemoMessage;
import com.example.demo.shared.constants.KafkaConstants;
import com.example.demo.shared.utils.JsonUtils;
import com.fasterxml.jackson.core.type.TypeReference;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class KafkaMessageConsumer {

  /**
   * - set num of concurrent consumers by concurrency
   */
  @KafkaListener(
      id = "message-consumer",
      concurrency = KafkaConstants.MESSAGE_CONCURRENCY,
      topics = KafkaConstants.MESSAGE_TOPIC)
  public void consume(List<String> messages) {
    for (String msg : messages) {
      try {
        DemoMessage demoMessage = JsonUtils.map(msg, new TypeReference<>() {});
        log.info("Received message {}", JsonUtils.toJson(demoMessage));
      } catch (Exception e) {
        log.error("> Cannot deserialize msg: {}, Exception: ", msg, e);
      }
    }
  }
}
