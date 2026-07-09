package com.rambabu.ai.exception;

import org.springframework.stereotype.Component;

import java.net.UnknownHostException;

@Component
public class AiExceptionTranslator {

    public AiServiceException translate(RuntimeException exception){

        Throwable rootCause = getRootCause(exception);
         if(rootCause instanceof UnknownHostException) {
             return new AiServiceException(
                     ErrorCode.NETWORK_ERROR,
                     exception);
         }
        return new AiServiceException(
                ErrorCode.INTERNAL_SERVER_ERROR,
                exception);
    }

    private Throwable getRootCause(Throwable throwable){

        if(throwable != null && throwable.getCause()!=null){
            return throwable.getCause();
        }
        return throwable;
    }
}
