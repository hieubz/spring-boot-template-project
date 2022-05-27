package com.example.demo.infrastructure.events;

import com.example.demo.infrastructure.repository.mongo.entity.DemoLog;
import com.example.demo.infrastructure.repository.mongo.primary.DemoLogRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@Async("threadPoolTaskExecutor2")
@RequiredArgsConstructor
public class NewProductEventListener implements ApplicationListener<NewProductEvent> {

  private final DemoLogRepository demoLogRepository;

  @Override
  public void onApplicationEvent(NewProductEvent event) {
    log.info("NewProductEventListener resolved {}", event);
    demoLogRepository.insert(DemoLog.builder().requestId(event.getRequestId()).build());
  }
}
