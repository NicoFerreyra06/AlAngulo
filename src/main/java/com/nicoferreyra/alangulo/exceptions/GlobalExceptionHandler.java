package com.nicoferreyra.alangulo.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFoundException(NotFoundException ex){
        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage(), LocalDateTime.now().toString());

        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorResponse> handleBadRequestException(BadRequestException ex){
        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage(), LocalDateTime.now().toString());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex){
        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage().toString()  , LocalDateTime.now().toString());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}
