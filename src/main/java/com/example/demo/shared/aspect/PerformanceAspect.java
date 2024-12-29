package com.example.demo.shared.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class PerformanceAspect {

  /** print execution time of every method calls in any class within the service package. */
  @Around("execution(* com.example.demo.core.service.*.*(..))")
  public Object executionTime(ProceedingJoinPoint joinPoint) throws Throwable {
    long startTime = System.currentTimeMillis();
    Object object = joinPoint.proceed();
    long endTime = System.currentTimeMillis();
    log.info(
        "Class name: {}. Method name: {}. Execution time = {} ms",
        joinPoint.getSignature().getDeclaringTypeName(),
        joinPoint.getSignature().getName(),
        endTime - startTime);
    return object;
  }
}
