package com.rambabu.ai.observability;

public enum AIMetricType {

    TOTAL_REQUEST("ai.requests.total"),

    RAG_REQUEST("ai.requests.rag"),

    MCP_REQUEST("ai.requests.mcp"),

    FAILED_REQUEST("ai.requests.failed");

    private final String metricName;

    AIMetricType(String metricName) {
        this.metricName = metricName;
    }

    public String metricName() {
        return metricName;
    }
}
