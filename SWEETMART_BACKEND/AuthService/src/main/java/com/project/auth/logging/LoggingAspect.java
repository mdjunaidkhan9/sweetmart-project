package com.project.auth.logging;

import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

    // ✅ Logs before any method in service and controller
    @Before("execution(* com.project.auth.service..*(..)) || execution(* com.project.auth.controller..*(..))")
    public void beforeMethods() {
        System.out.println("Method execution started.");
    }

    // ✅ Logs after any method in service and controller
    @After("execution(* com.project.auth.service..*(..)) || execution(* com.project.auth.controller..*(..))")
    public void afterMethods() {
        System.out.println("Method execution completed.");
    }

    // ❌ Logs if an exception occurs in service or controller methods
    @AfterThrowing(pointcut = "execution(* com.project.auth.service..*(..)) || execution(* com.project.auth.controller..*(..))", throwing = "ex")
    public void afterExceptionThrown(Exception ex) {
        System.out.println("Exception occurred: " + ex.getMessage());
    }
}
