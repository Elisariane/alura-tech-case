package com.elisariane.aluratechcase.infra.exception;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ErrorException {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity getErrorNotFound() {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity getErrorBadRequest(MethodArgumentNotValidException exception) {
        var errors = exception.getFieldErrors();
        return ResponseEntity.badRequest().body(errors.stream().map(DataValidationErrors::new).toList());
    }


    private record DataValidationErrors(String field, String message) {
        public DataValidationErrors(FieldError error){
            this(error.getField(), error.getDefaultMessage());
        }
    }

}
