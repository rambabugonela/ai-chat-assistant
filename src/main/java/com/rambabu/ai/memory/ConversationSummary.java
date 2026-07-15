package com.rambabu.ai.memory;

import java.time.Instant;

public record ConversationSummary(
        String content,
        Instant updatedAt
) {}
