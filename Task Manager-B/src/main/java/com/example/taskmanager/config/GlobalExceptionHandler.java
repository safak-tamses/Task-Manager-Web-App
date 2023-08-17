package com.example.taskmanager.config;


import com.example.taskmanager.error.UserLoginException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(UserLoginException.class)
    public ResponseEntity<String> handleCustomErrorCodeException(UserLoginException ex) {
        return ResponseEntity.status(330).body(ex.getMessage());
    }
}
