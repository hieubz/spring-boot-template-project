package com.example.demo.infrastructure.msg_queue;

public interface MessageQueuePublisher {

  void publish(String topic, Message message) throws MessageQueueException;
}
