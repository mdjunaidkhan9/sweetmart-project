package com.project.cart.logging;

import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

    @Before("execution(* com.project.cart.service.*.*(..)) || execution(* com.project.cart.controller.*.*(..))")
    public void beforeMethods() {
        System.out.println("Method execution started.");
    }

    @After("execution(* com.project.cart.service.*.*(..)) || execution(* com.project.cart.controller.*.*(..))")
    public void afterMethods() {
        System.out.println("Method execution completed.");
    }

    @AfterThrowing(pointcut = "execution(* com.project.cart.service.*.*(..)) || execution(* com.project.cart.controller.*.*(..))", throwing = "ex")
    public void afterExceptionThrown(Exception ex) {
        System.out.println("Exception occurred: " + ex.getMessage());
    }
}
