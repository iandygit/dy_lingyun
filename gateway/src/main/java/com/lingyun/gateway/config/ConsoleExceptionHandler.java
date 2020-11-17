package com.lingyun.gateway.config;

import org.springframework.expression.AccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ConsoleExceptionHandler {


    @ExceptionHandler(AccessException.class)
    private ResponseEntity<String> handleAccessException(AccessException e){

        return  ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
    }
}
