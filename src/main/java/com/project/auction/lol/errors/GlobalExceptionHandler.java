package com.project.auction.lol.errors;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @Autowired
    ObjectMapper objectMapper;

    @ExceptionHandler(value = MayoException.class)
    public ResponseEntity<ErrorResponse> handleMayoException(MayoException e) {
        ErrorResponse er = getErrorResponse(e.getMessage(), e.getCode());
        log.error("handleMayoException[{}]", er);
        return ResponseEntity
                .status(e.getCode().getStatus())
                .body(er);
    }

    @ExceptionHandler(value = RuntimeException.class)
    public ResponseEntity<ErrorResponse> handleRuntimeException(RuntimeException e) {

        /*
        format of e.getCause().getMessage()의 형식이
        detailMessage : com.project.auction.lol.who.throws.exception;
        다음과 같아 프로젝트 내부 구조를 보여주지 않기 위해 parsing하였다.
         */
        ErrorCode errorCode = ErrorCode.INTERNAL_SERVER_ERROR;
        String detailMessage = e.toString();
        if (e.getMessage() != null) detailMessage = e.getMessage().split(":")[0];
        if (e.getCause() != null) detailMessage = e.getMessage().split(":")[0];

        ErrorResponse er = getErrorResponse(detailMessage, errorCode);
        log.error("handleMayoException[{}]", e.getLocalizedMessage());
        return ResponseEntity.status(errorCode.getStatus())
                .body(er);
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(MethodArgumentNotValidException e) {
        ErrorCode errorCode = ErrorCode.INVALID_INPUT_VALUE;
        ErrorResponse er = getErrorResponse(e, errorCode);
        log.error("handleValidationException[{}]", er);
        return ResponseEntity
                .status(errorCode.getStatus())
                .body(er);
    }

    public static ErrorResponse getErrorResponse(String message, ErrorCode code) {
        return ErrorResponse.builder()
                .code(code.getCode())
                .message(message)
                .status(code.getStatus())
                .build();
    }

    public static ErrorResponse getErrorResponse(BindException e, ErrorCode code) {

        List<ErrorResponse.ValidationError> validationErrorList = e.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(ErrorResponse.ValidationError::of)
                .collect(Collectors.toList());

        return ErrorResponse.builder()
                .code(code.getCode())
                .message(code.getMessage())
                .errors(validationErrorList)
                .status(code.getStatus())
                .build();
    }
}
