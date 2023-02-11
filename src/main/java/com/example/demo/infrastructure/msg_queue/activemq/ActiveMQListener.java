package com.example.demo.infrastructure.msg_queue.activemq;

import com.example.demo.infrastructure.msg_queue.vo.DemoMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ActiveMQListener {

  @JmsListener(destination = "demo-queue")
  public void receiveMessage(DemoMessage message) {
    log.info("Received message with id = {}", message.getId());
  }
}
