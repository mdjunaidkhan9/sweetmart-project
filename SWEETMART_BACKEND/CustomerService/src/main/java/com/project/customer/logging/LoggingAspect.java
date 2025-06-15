package com.project.customer.logging;

import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;
@Aspect
@Component
public class LoggingAspect {

    // 📝 Log before every method in service or controller
    @Before("execution(* com.project.customer.service.*.*(..)) || execution(* com.project.customer.controller.*.*(..))")
    public void beforeMethods() {
        System.out.println("✅ Method execution started.");
    }

    // ✅ Log after every method in service or controller
    @After("execution(* com.project.customer.service.*.*(..)) || execution(* com.project.customer.controller.*.*(..))")
    public void afterMethods() {
        System.out.println("✅ Method execution completed.");
    }

    // ❌ Log exceptions from service or controller
    @AfterThrowing(pointcut = "execution(* com.project.customer.service.*.*(..)) || execution(* com.project.customer.controller.*.*(..))", throwing = "ex")
    public void afterExceptionThrown(Exception ex) {
        System.out.println("❌ Exception occurred: " + ex.getMessage());
    }
}

