package com.rambabu.ai.observability;

public interface AIObservabilityService {

    void increment(AIMetricType metric);

    void recordDuration(long durationInMillis);

}