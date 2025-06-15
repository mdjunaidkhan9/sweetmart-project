package com.project.payment.logging;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

    // Log before any controller method is executed
    @Before("execution(* com.project.payment.controller.*.*(..))")
    public void logBeforeController(JoinPoint joinPoint) {
        System.out.println("[Controller] Entering - " + joinPoint.getSignature());
    }

    // Log after controller method returns
    @AfterReturning(pointcut = "execution(* com.project.payment.controller.*.*(..))", returning = "result")
    public void logAfterController(JoinPoint joinPoint, Object result) {
        System.out.println("[Controller] Exiting - " + joinPoint.getSignature() + " with result: " + result);
    }

    // Log before any service method is executed
    @Before("execution(* com.project.payment.service.*.*(..))")
    public void logBeforeService(JoinPoint joinPoint) {
        System.out.println("[Service] Entering - " + joinPoint.getSignature());
    }

    // Log after service method returns
    @AfterReturning(pointcut = "execution(* com.project.payment.service.*.*(..))", returning = "result")
    public void logAfterService(JoinPoint joinPoint, Object result) {
        System.out.println("[Service] Exiting - " + joinPoint.getSignature() + " with result: " + result);
    }

    // Optional: Log exceptions in both layers
    @AfterThrowing(pointcut = "execution(* com.project.payment..*(..))", throwing = "error")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable error) {
        System.err.println("[Exception] in - " + joinPoint.getSignature() + " with error: " + error.getMessage());
    }
}
