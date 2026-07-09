package com.rambabu.ai.dto;

import autovalue.shaded.org.checkerframework.checker.nullness.qual.MonotonicNonNull;
import com.rambabu.ai.prompt.PromptType;
import jakarta.validation.constraints.NotBlank;

public record ChatRequest(
        @MonotonicNonNull
        String sessionId,
        @NotBlank(message = "Message")
        String message,
        @MonotonicNonNull
        PromptType promptType
) {
}
