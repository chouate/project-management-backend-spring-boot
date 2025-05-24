package com.hps.userservice.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Map<Object, String>> ressourceNotFoundException(ResourceNotFoundException exception){
        Map<Object,String> error = new HashMap<>();
        error.put(HttpStatus.NOT_FOUND, exception.getMessage());
        return ResponseEntity.ok(error);
    }

    @ExceptionHandler(ResourceAlreadyExistException.class)
    public ResponseEntity<Map<HttpStatus, String>> resourceAlreadyExist(ResourceAlreadyExistException exception){
        Map<HttpStatus, String> error = new HashMap<>();
        error.put(HttpStatus.CONFLICT, exception.getMessage());
        return ResponseEntity.ok(error);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Map<String, String>> handleAccessDeniedException(AccessDeniedException ex) {
        Map<String, String> response = new HashMap<>();
        response.put("error", "ACCESS_DENIED");
        response.put("message", ex.getMessage());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptins(MethodArgumentNotValidException exception){
        Map<String, String> errors = new HashMap<>();
        exception.getBindingResult().getFieldErrors().forEach(error -> {
            errors.put(error.getField(), error.getDefaultMessage());
        });
        return new  ResponseEntity<>(errors,HttpStatus.BAD_REQUEST );
    }
}

