package com.example.demo.infrastructure.msg_queue.activemq;

import com.example.demo.infrastructure.msg_queue.vo.DemoMessage;
import com.example.demo.shared.exception.ActiveMQPublishException;
import com.example.demo.shared.utils.JsonUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class ActiveMQProducer {

  private final JmsTemplate jmsTemplate;

  @Retryable(
      value = ActiveMQPublishException.class,
      maxAttemptsExpression = "${retry.maxAttempts}",
      backoff = @Backoff(delayExpression = "${retry.backOffDelay}"))
  public void sendMessage(String queueName, DemoMessage message) throws ActiveMQPublishException {
    try {
      log.info("> ActiveMQProducer sending message: {}", JsonUtils.toJson(message));
      jmsTemplate.send(queueName, session -> session.createObjectMessage(message));
    } catch (Exception e) {
      log.warn("> ActiveMQProducer failure in sending the message id {}", message.getId());
      throw new ActiveMQPublishException();
    }
  }
}
