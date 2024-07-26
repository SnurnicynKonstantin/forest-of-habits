package com.example.forestofhabits.controller;

import com.example.forestofhabits.controller.dto.ExceptionDto;
import com.example.forestofhabits.exception.AuthException;
import com.example.forestofhabits.exception.ValidateException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.persistence.EntityNotFoundException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AuthException.class)
    public ResponseEntity<?> handleUnauthorizedException(Exception e) {
        ExceptionDto response = ExceptionDto.builder().message(e.getMessage()).build();
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<?> handleNotFoundException(Exception e) {
        ExceptionDto response = ExceptionDto.builder().message(e.getMessage()).build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(ValidateException.class)
    public ResponseEntity<?> handleValidationException(Exception e) {
        ExceptionDto response = ExceptionDto.builder().message(e.getMessage()).build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
}
