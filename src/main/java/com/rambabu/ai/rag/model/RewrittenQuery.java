package com.rambabu.ai.rag.model;

public record RewrittenQuery(
        String question,
        RetrievalMode retrievalMode
) {
}