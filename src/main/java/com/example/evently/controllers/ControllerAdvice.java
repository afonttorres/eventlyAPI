package com.example.evently.controllers;

import com.example.evently.dto.error.ErrorDto;
import com.example.evently.exceptions.BadReqEx;
import com.example.evently.exceptions.NotFoundEx;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerAdvice {
    @ExceptionHandler(value = NotFoundEx.class)
    public ResponseEntity<ErrorDto> notFoundExHandler(NotFoundEx e){
        var err = ErrorDto.builder()
                .code(e.getCode())
                .message(e.getMessage())
                .emoji("🔎")
                .build();
        return new ResponseEntity<>(err, e.getHttpStatus());
    }

    @ExceptionHandler(value = BadReqEx.class)
    public ResponseEntity<ErrorDto> notFoundExHandler(BadReqEx e){
        var err = ErrorDto.builder()
                .code(e.getCode())
                .message(e.getMessage())
                .emoji("❌")
                .build();
        return new ResponseEntity<>(err, e.getHttpStatus());
    }
}
