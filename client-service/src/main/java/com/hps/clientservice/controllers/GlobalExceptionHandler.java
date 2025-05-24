package com.hps.clientservice.controllers;

import com.hps.clientservice.exceptions.ResourceAlreadyExistException;
import com.hps.clientservice.exceptions.ResourceNotFoundException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.HashMap;
import java.util.Map;


import static com.hps.clientservice.MyHttpResponse.response;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptins(MethodArgumentNotValidException exception){
        Map<String, String> errors = new HashMap<>();
        exception.getBindingResult().getFieldErrors().forEach(error -> {
            errors.put(error.getField(), error.getDefaultMessage());
        });
        return new  ResponseEntity<>(errors,HttpStatus.BAD_REQUEST );
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

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Object> resourceNotFoundException(ResourceNotFoundException exception)
    {
        return response(HttpStatus.NOT_FOUND, exception.getMessage(), null);
    }
    @ExceptionHandler(ResourceAlreadyExistException.class)
    public ResponseEntity<Object> resourceAlreadyExistException(ResourceAlreadyExistException exception)
    {
        //Cette fois le code http est 409, ce qui indique qu'il y'a un conflit
        return response(HttpStatus.CONFLICT, exception.getMessage(), null);
    }

    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public ResponseEntity<Object> handleSqlIntegrityViolation(SQLIntegrityConstraintViolationException e) {
        String message = "Database constraint violation: " + e.getMessage();
        return new ResponseEntity<>(Map.of("error", message), HttpStatus.CONFLICT);
    }

    //    // On definit le type d'exception
//    @ExceptionHandler(MethodArgumentNotValidException.class)
//    public ResponseEntity<Object> methodArgumentNotValidException(MethodArgumentNotValidException exception)
//    {
//        // On crée un map qui va contenir le nom des champs et le message qui ne repondent pas aux contraintes
//        Map<String, String> errors = exception.getBindingResult()
//                .getFieldErrors()
//                .stream()
//                .collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage));
//
//        // On retourne la reponse avec le code, et la liste des erreurs
//        return response(HttpStatus.BAD_REQUEST, "NOK", errors);
//    }


}
