package com.rambabu.ai.memory;

import java.time.Instant;

public record Message(
        MessageRole role,
        String content,
        Instant timeStamp
) {
}
