package com.rambabu.ai.exception;

import org.springframework.stereotype.Component;

import java.net.ConnectException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

@Component
public class AiExceptionTranslator {

    public AiServiceException translate(RuntimeException exception){

         if(isNetworkException(exception)) {
             return new AiServiceException(
                     ErrorCode.NETWORK_ERROR,
                     exception);
         }
        return new AiServiceException(
                ErrorCode.INTERNAL_SERVER_ERROR,
                exception);
    }

    private boolean isNetworkException(Throwable throwable) {

        return containsCause(throwable, UnknownHostException.class)
                || containsCause(throwable, SocketException.class)
                || containsCause(throwable, ConnectException.class)
                || containsCause(throwable, SocketTimeoutException.class);
    }

    private boolean containsCause(Throwable throwable,
                                  Class<? extends Throwable> type) {
        Throwable current = throwable;
        while (current != null) {
            if (type.isInstance(current)) {
                return true;
            }
            current = current.getCause();
        }
        return false;
    }
}
