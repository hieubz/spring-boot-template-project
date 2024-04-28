package com.example.demo.infrastructure.config.kafka;

import java.net.SocketTimeoutException;
import java.util.HashMap;
import java.util.Map;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.kafka.retrytopic.RetryTopicConfiguration;
import org.springframework.kafka.retrytopic.RetryTopicConfigurationBuilder;
import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer;
import org.springframework.util.backoff.BackOff;
import org.springframework.util.backoff.FixedBackOff;

@EnableKafka
@Configuration
public class KafkaConsumerConfig {

  @Value("${spring.kafka.bootstrap-servers}")
  private String bootstrapServers;

  @Value("${kafka.consumer.config.group-id}")
  private String groupId;

  @Value(value = "${kafka.backoff.interval}")
  private Long interval;

  @Value(value = "${kafka.backoff.max_failure}")
  private Long maxAttempts;

  @Bean
  public ConsumerFactory<String, String> consumerFactory() {
    Map<String, Object> props = new HashMap<>();
    props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
    props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
    props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
    // fix this "infinite loop" problem by ErrorHandlingDeserializer
    props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, ErrorHandlingDeserializer.class);
    props.put(ErrorHandlingDeserializer.VALUE_DESERIALIZER_CLASS, StringDeserializer.class);
    props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest");
    return new DefaultKafkaConsumerFactory<>(props);
  }

  @Bean
  public ConcurrentKafkaListenerContainerFactory<String, String> kafkaListenerContainerFactory() {
    ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
    factory.setConsumerFactory(consumerFactory());
    // receive the entire batch of consumer records received from the consumer poll
    factory.setBatchListener(true);
    // If a retry policy is present, we’ll set the ack mode to AckMode.RECORD
    // to make sure that the consumer will redeliver messages if an error happens during processing.
    factory.setCommonErrorHandler(blockingRetryErrorHandler());
    factory.getContainerProperties().setAckMode(ContainerProperties.AckMode.RECORD);
    return factory;
  }

  /**
   * In the blocking retry, when message processing fails, the consumer blocks until the retry mechanism finishes its retries,
   * or until the maximum number of retries is reached.
   * => Keep the order of messages, however it causes delays.
   * Notes:
   * - consumerRecord: represents the Kafka record that caused the error.
   * - exception: represents the exception that was thrown.
   * - If we don’t set any retryable exceptions, the default set of retryable exceptions will be used:
   *   MessagingException
   *   RetryableException
   *   ListenerExecutionFailedException
   *
   */
  @Bean
  public DefaultErrorHandler blockingRetryErrorHandler() {
    BackOff fixedBackOff = new FixedBackOff(interval, maxAttempts);
    DefaultErrorHandler errorHandler = new DefaultErrorHandler((consumerRecord, e) -> {
              // logic to execute when all the retry attempts are failed
    }, fixedBackOff);
    // specify which exceptions are retryable and which are non-retryable.
    errorHandler.addRetryableExceptions(SocketTimeoutException.class);
    errorHandler.addNotRetryableExceptions(NullPointerException.class);
    return errorHandler;
  }

  /**
   * The DLT handler method can also be provided through the
   * RetryTopicConfigurationBuilder.dltHandlerMethod(String, String) method, passing as arguments
   * the bean name and method name that should process the DLT’s messages.
   * * *
   * Notes: we can also configure factory, backoff, maxAttempts, retryOn exceptions,...
   * in the RetryTopicConfiguration bean
   */
  @Bean
  public RetryTopicConfiguration myRetryTopic(KafkaTemplate<String, String> template) {
    return RetryTopicConfigurationBuilder.newInstance()
        .dltHandlerMethod("myCustomDltProcessor", "processDltMessage")
        .create(template);
  }
}
