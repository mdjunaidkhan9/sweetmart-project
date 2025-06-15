package com.project.product.aop;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.JoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

    private final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    @Before("execution(* com.project.product.controller.*.*(..)) || execution(* com.project.product.service.*.*(..))")
    public void logBefore(JoinPoint joinPoint) {
        logger.info("Executing: " + joinPoint.getSignature().toShortString());
    }

    @AfterReturning(value = "execution(* com.project.product.service.*.*(..))", returning = "result")
    public void logAfterReturning(JoinPoint joinPoint, Object result) {
        logger.info("Method completed: {} with result {}", joinPoint.getSignature().toShortString(), result);
    }

    @AfterThrowing(value = "execution(* com.project.product.service.*.*(..))", throwing = "error")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable error) {
        logger.error("Exception in {}: {}", joinPoint.getSignature().toShortString(), error.getMessage());
    }
}
