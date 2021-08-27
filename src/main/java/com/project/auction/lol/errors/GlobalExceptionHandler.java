package com.project.auction.lol.errors;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

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
        String detailMessage = e.getCause().getMessage().split(":")[0];

        ErrorResponse errorResponse = ErrorResponse.builder()
                .message(detailMessage)
                .status(HttpStatus.BAD_REQUEST)
                .build();
        log.error(e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(errorResponse);
    }

}
