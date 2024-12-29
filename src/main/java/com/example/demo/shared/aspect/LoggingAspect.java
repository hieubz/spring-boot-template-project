package com.example.demo.shared.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class LoggingAspect {

  // Define the point cut to avoid duplication
  @Pointcut("execution(* com.example.demo.core.service.*.*(..))")
  public void serviceMethods() {}

  /** print log before every method calls in any class within the service package. */
  @Before("serviceMethods()")
  public void logBeforeMethod(JoinPoint joinPoint) {
    log.info("Executing method: " + joinPoint.getSignature().getName());
  }

  /** print log after every method calls in any class within the service package. */
  @After("serviceMethods()")
  public void logAfterMethod(JoinPoint joinPoint) {
    log.info("Exiting method: " + joinPoint.getSignature().getName());
  }
}
