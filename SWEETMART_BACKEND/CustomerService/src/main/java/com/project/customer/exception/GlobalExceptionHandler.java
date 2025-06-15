package com.project.customer.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // 🔴 Handles client-side input errors
    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleInvalidArguments(IllegalArgumentException e) {
        return "Error: " + e.getMessage();
    }

    // 🔴 Handles server-side exceptions
    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String handleGenericExceptions(RuntimeException e) {
        return "Internal Server Error: " + e.getMessage();
    }
}
