package com.rambabu.ai.exception;

import lombok.Getter;

@Getter
public class AiServiceException extends RuntimeException{

    private final ErrorCode errorCode;


    public AiServiceException(ErrorCode errorCode) {
        super(errorCode.getDefaultMessage());
        this.errorCode = errorCode;
    }

    public AiServiceException(ErrorCode errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public AiServiceException(ErrorCode errorCode, Throwable cause) {
        super(cause);
        this.errorCode = errorCode;
    }
}
