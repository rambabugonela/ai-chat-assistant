package com.rambabu.ai.observability;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class DefaultAIObservabilityService
        implements AIObservabilityService {

    private final MeterRegistry meterRegistry;

    private final Timer responseTimer;

    public DefaultAIObservabilityService(
            MeterRegistry meterRegistry) {

        this.meterRegistry = meterRegistry;

        this.responseTimer =
                Timer.builder("ai.response.duration")
                        .description("AI Response Duration")
                        .publishPercentileHistogram()
                        .register(meterRegistry);
    }


    @Override
    public void increment(AIMetricType metric) {
        log.info("Incrementing metric : {}", metric.metricName());
        meterRegistry
                .counter(metric.metricName())
                .increment();
    }

    @Override
    public void recordDuration(long durationInMillis) {

        responseTimer.record(durationInMillis, TimeUnit.MILLISECONDS);
    }


}