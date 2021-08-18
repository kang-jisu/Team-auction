package com.project.auction.lol.errors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MayoException.class)
    public ResponseEntity<String> handleException(MayoException e){
        return ResponseEntity
                .status(e.getHttpStatus().value())
                .body(e.getMessage());
    }
}
