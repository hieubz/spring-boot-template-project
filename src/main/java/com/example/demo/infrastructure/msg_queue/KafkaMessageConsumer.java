package com.example.demo.infrastructure.msg_queue;

import com.example.demo.infrastructure.msg_queue.vo.DemoMessage;
import com.example.demo.shared.constants.KafkaConstants;
import com.example.demo.shared.utils.JsonUtils;
import com.fasterxml.jackson.core.type.TypeReference;
import java.net.SocketTimeoutException;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.errors.SerializationException;
import org.springframework.kafka.annotation.DltHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.kafka.retrytopic.DltStrategy;
import org.springframework.kafka.support.converter.ConversionException;
import org.springframework.kafka.support.serializer.DeserializationException;
import org.springframework.messaging.converter.MessageConversionException;
import org.springframework.messaging.handler.invocation.MethodArgumentResolutionException;
import org.springframework.retry.annotation.Backoff;
import org.springframework.stereotype.Component;

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

  /**
   * We customized the retry behavior by modifying several properties, such as:
   * 1. backoff: This property specifies the backoff strategy to use when retrying a failed message.
   * 2. attempts: This property specifies the maximum number of times a message should be retried before giving up.
   * 3. autoCreateTopics: This property specifies whether to automatically create the retry topic and
   * 4. DLT (Dead Letter Topic) if they don’t already exist.
   * 5. include: This property specifies the exceptions that should trigger a retry.
   * 6. exclude: This property specifies the exceptions that shouldn’t trigger a retry.
   * * * *
   * Notes: If a retry policy is present, we should handle messages one by one instead of batching
   * so that the retry policy is able to handle errors properly
   */
  @RetryableTopic(
      backoff = @Backoff(value = 300, multiplier = 2, maxDelay = 0),
      attempts = "3",
      autoCreateTopics = "false",
      dltStrategy = DltStrategy.FAIL_ON_ERROR, // Fail if DLT processing throws an error.
      include = SocketTimeoutException.class, // Can remove include/exclude in reality
      exclude = {
        NullPointerException.class,
        SerializationException.class,
        DeserializationException.class,
        ClassCastException.class,
        ConversionException.class,
        MessageConversionException.class,
        MethodArgumentResolutionException.class,
        NoSuchMethodException.class
      })
  @KafkaListener(topics = KafkaConstants.MESSAGE_TOPIC, groupId = "test")
  public void consume2(String message) {
    System.out.println("Received Message in group test: " + message);
  }

  /** use the @DltHandler annotation in a method of the class with the @RetryableTopic annotation */
  @DltHandler
  public void processMessage(String message) {
    // ... message processing, persistence, etc
    System.out.println("Handled the message in DLT: " + message);
  }
}
