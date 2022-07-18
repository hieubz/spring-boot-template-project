package com.example.demo.infrastructure.msg_queue;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service("KafkaPublisher")
@RequiredArgsConstructor
public class KafkaPublisher implements MessageQueuePublisher {

  private final KafkaTemplate kafkaTemplate;

  @Override
  public void publish(String topic, Message message) throws MessageQueueException {
    kafkaTemplate.send(topic, message);
  }
}
