package com.rambabu.ai.exception;

import com.rambabu.ai.observability.CorrelationContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AiServiceException.class)
    public ResponseEntity<ErrorResponse> handleAiServiceException(final AiServiceException aiServiceException) {
        ErrorCode errorCode = aiServiceException.getErrorCode();

        ErrorResponse errorResponse = new ErrorResponse(
                Instant.now(),
                errorCode.getErrorCode(),
                errorCode.getHttpStatus().value(),
                errorCode.getDefaultMessage()
        );
        log.error( "Exception RequestId={} Exception={} Message={}",
                CorrelationContext.getRequestId(),
                aiServiceException.getClass().getSimpleName(),
                aiServiceException.getMessage(),
                aiServiceException);
        return ResponseEntity.status(errorCode.getHttpStatus()).body(errorResponse);
    }
}
