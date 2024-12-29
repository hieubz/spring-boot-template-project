package com.example.demo.shared.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class ExecutionTimeAdvice {

  /**
   * Around: define how the advice work (before and after the method invocation)
   * @annotation(...): define the pointcut where the advice will be applied (annotated with the @TrackExecutionTime)
   * @param point: joint point that provides access to the method being intercepted
   */
  @Around("@annotation(com.example.demo.shared.annotation.TrackExecutionTime)")
  public Object executionTime(ProceedingJoinPoint point) throws Throwable {
    long startTime = System.currentTimeMillis();
    Object object = point.proceed();
    long endTime = System.currentTimeMillis();
    log.info(
        "Class Name: {}. Method Name: {}. Execution time = {} ms",
        point.getSignature().getDeclaringTypeName(),
        point.getSignature().getName(),
        (endTime - startTime));
    return object;
  }
}
