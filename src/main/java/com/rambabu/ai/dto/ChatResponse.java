package com.rambabu.ai.dto;

import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;

public record ChatResponse(
        @NotBlank(message = "Message")
        String response,
        String model,
        LocalDateTime timeStamp,
        long processingTime

) {
}
