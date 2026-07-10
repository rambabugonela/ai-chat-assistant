package com.rambabu.ai.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
@Getter
public enum ErrorCode {
    NETWORK_ERROR("AI-001", HttpStatus.SERVICE_UNAVAILABLE,"Service Unavailable"),
    RATE_LIMIT_EXCEEDED("AI-002", HttpStatus.TOO_MANY_REQUESTS,"Rate limit exceeded"),
    INVALID_PROMPT("AI-003", HttpStatus.BAD_REQUEST,"Invalid Prompt"),
    INTERNAL_SERVER_ERROR("AI-004", HttpStatus.INTERNAL_SERVER_ERROR,"Internal server"),
    ;

    private final String errorCode;
    private final HttpStatus httpStatus;
    private final String defaultMessage;
    ErrorCode(String errorCode, HttpStatus httpStatus, String defaultMessage) {
        this.errorCode = errorCode;
        this.httpStatus = httpStatus;
        this.defaultMessage = defaultMessage;
    }

}
