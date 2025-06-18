package com.hps.taskservice.exceptions;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import feign.FeignException;
import feign.FeignException.NotFound;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Object> handelNotFound(ResourceNotFoundException ex){
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("message", ex.getMessage());

        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Map<String, String >> handleConstraintViolationException(ConstraintViolationException exception){
        Map<String, String> errors = new HashMap<>();
        exception.getConstraintViolations().forEach(constraintViolation -> {
            String field = constraintViolation.getPropertyPath().toString();
            String message = constraintViolation.getMessage();
            errors.put(field, message);
        });
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(FeignException.NotFound.class)
    public ResponseEntity<Object> handleFeignNotFoundException(FeignException.NotFound ex) {
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now());

        try {
            // Parser le contenu JSON de l'exception
            ObjectMapper mapper = new ObjectMapper();
            Map<String, Object> errorMap = mapper.readValue(ex.contentUTF8(), Map.class);

            // Récupérer le message d'erreur renvoyé par le service distant
            body.put("message", errorMap.get("message"));
        } catch (Exception parseException) {
            // En cas d'erreur de parsing, on met un message par défaut
            body.put("message", "Erreur 404 depuis le service distant.");
        }

        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }
//    public ResponseEntity<Object> handleFeignNotFoundException(FeignException.NotFound ex) {
//        Map<String, Object> body = new HashMap<>();
//        body.put("timestamp", LocalDateTime.now());
//        body.put("message", "Le projet est introuvable dans le service distant.");
//        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
//    }

}
