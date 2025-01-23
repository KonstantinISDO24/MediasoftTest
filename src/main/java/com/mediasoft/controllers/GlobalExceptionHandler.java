package com.mediasoft.controllers;
import com.mediasoft.exception.ProductNotFoundException;
import org.postgresql.util.PSQLException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.HashMap;

@RestControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<?>handleException(ProductNotFoundException exception) {
       return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
               generateExceptionResponse(exception, HttpStatus.NOT_FOUND)
       );
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<?>handleException(MethodArgumentTypeMismatchException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                generateExceptionResponse(exception, HttpStatus.BAD_REQUEST)
        );
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<?> handleException(HttpMessageNotReadableException exception){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
            generateExceptionResponse(exception, HttpStatus.BAD_REQUEST)
        );
    }

    @ExceptionHandler(PSQLException.class)
    public ResponseEntity<?> handleException(PSQLException exception){
        return ResponseEntity.status(HttpStatus.CONFLICT).body(
                generateExceptionResponse(exception, HttpStatus.CONFLICT)
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleException(MethodArgumentNotValidException exception) {
        HashMap<String, Object> response = new HashMap<>();
        for (FieldError error : exception.getBindingResult().getFieldErrors()) {
            response.put(error.getField(), error.getDefaultMessage());
        }
        response.put("status", HttpStatus.CONFLICT.value());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }

    private HashMap<String, Object> generateExceptionResponse(Exception exception, HttpStatus status) {
        HashMap<String, Object> response = new HashMap<>();
        response.put("status", status.value());
        response.put("message", exception.getMessage());
        return response;
    }
}
