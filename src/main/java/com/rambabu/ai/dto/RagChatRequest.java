package com.rambabu.ai.dto;

import jakarta.validation.constraints.NotBlank;

public record RagChatRequest(

        String sessionId,
        @NotBlank(message = "Question is required")
        String message

) {
}
