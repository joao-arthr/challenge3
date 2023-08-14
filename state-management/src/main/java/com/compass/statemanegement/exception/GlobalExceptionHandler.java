package com.compass.statemanegement.exception;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<String> handleConstraintViolationException(ConstraintViolationException ex) {
        StringBuilder errorMessage = new StringBuilder("Validation errors:\n");

        for (ConstraintViolation<?> violation : ex.getConstraintViolations()) {
            String fieldName = ((FieldError) violation.getLeafBean()).getField();
            String message = violation.getMessage();
            errorMessage.append(fieldName).append(": ").append(message).append("\n");
        }

        return ResponseEntity.badRequest().body(errorMessage.toString());
    }
}
