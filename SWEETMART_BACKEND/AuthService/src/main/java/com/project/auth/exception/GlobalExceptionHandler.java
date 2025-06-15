package com.project.auth.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestControllerAdvice // Globally handles exceptions across controllers
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class) // Catches RuntimeException
    @ResponseStatus(HttpStatus.BAD_REQUEST)   // Responds with 400 Bad Request
    public String handleRuntimeException(RuntimeException e) {
        return "Error: " + e.getMessage(); // Returns error message as plain text
    }
}
