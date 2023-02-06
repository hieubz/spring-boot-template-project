package com.example.demo;

import com.example.demo.infrastructure.msg_queue.KafkaPublisher;
import com.example.demo.infrastructure.msg_queue.vo.DemoMessage;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

@SpringBootTest
public class KafkaPublisherTest {

  @Value("${kafka.consumer.messages.topic}")
  private String demoTopic;

  @Autowired private KafkaPublisher kafkaPublisher;

  @Test
  void test() {
    DemoMessage message = DemoMessage.builder().id(1L).created(LocalDateTime.now()).build();
    kafkaPublisher.publish(demoTopic, message);
  }
}
