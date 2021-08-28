package com.project.auction.lol.errors;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @Autowired
    ObjectMapper objectMapper;

    @ExceptionHandler(value = MayoException.class)
    public ResponseEntity<ErrorResponse> handleMayoException( MayoException e){
        ErrorResponse errorResponse = ErrorResponse.builder()
                .code(e.getCode().getCode())
                .message(e.getMessage())
                .status(e.getCode().getStatus())
                .build();
        log.error(errorResponse.toString() );
        return ResponseEntity.status(e.getCode().getStatus())
                .body(errorResponse);
    }

    @ExceptionHandler(value = RuntimeException.class)
    public ResponseEntity<ErrorResponse> handleRuntimeException( RuntimeException e){

        /*
        format of e.getCause().getMessage()의 형식이
        detailMessage : com.project.auction.lol.who.throws.exception;
        다음과 같아 프로젝트 내부 구조를 보여주지 않기 위해 parsing하였다.
         */
        String detailMessage = e.toString();
        if(e.getMessage()!=null) detailMessage = e.getMessage().split(":")[0];
        if(e.getCause()!=null) detailMessage = e.getMessage().split(":")[0];

        ErrorResponse errorResponse = ErrorResponse.builder()
                .message(detailMessage)
                .status(HttpStatus.BAD_REQUEST)
                .build();
        log.error(detailMessage);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(errorResponse);
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationException (MethodArgumentNotValidException e){
        String detailMessage = e.toString();
        if(e.hasErrors()) detailMessage = e.getAllErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.joining(","));
        ErrorResponse errorResponse = ErrorResponse.builder()
                .message(detailMessage)
                .status(HttpStatus.BAD_REQUEST)
                .build();
        log.error(detailMessage);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(errorResponse);
    }


}
