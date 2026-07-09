package com.rambabu.ai.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleAiServiceException(final AiServiceException aiServiceException){
        ErrorCode errorCode = aiServiceException.getErrorCode();

        ErrorResponse errorResponse = new ErrorResponse(
                Instant.now(),
                errorCode.getErrorCode(),
                errorCode.getHttpStatus().value(),
                errorCode.getDefaultMessage()
        );
        return ResponseEntity.status(errorCode.getHttpStatus()).body(errorResponse);
    }
}
