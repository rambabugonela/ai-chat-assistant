package com.rambabu.ai.exception;

import java.time.Instant;

public record ErrorResponse(
        Instant timeStamp,
        String errorCode,
        int status,
        String message
) {
}
