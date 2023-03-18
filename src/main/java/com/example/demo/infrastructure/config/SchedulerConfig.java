package com.example.demo.infrastructure.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableScheduling
@ConditionalOnProperty(prefix = "com.example.demo.scheduling", name="enabled", havingValue="true", matchIfMissing = true)
public class SchedulerConfig {}
