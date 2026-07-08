package com.rambabu.ai.dto;

import jakarta.validation.constraints.NotBlank;

public record ChatResponse(
        @NotBlank(message = "Message")
        String message
) {
}
