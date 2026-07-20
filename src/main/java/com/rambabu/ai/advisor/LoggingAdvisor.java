package com.rambabu.ai.advisor;

import com.rambabu.ai.observability.CorrelationContext;
import org.jspecify.annotations.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.ChatClientRequest;
import org.springframework.ai.chat.client.ChatClientResponse;
import org.springframework.ai.chat.client.advisor.api.CallAdvisor;
import org.springframework.ai.chat.client.advisor.api.CallAdvisorChain;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
public class LoggingAdvisor implements CallAdvisor {

    private static final Logger log =
            LoggerFactory.getLogger(LoggingAdvisor.class);

    @Override
    public @NonNull ChatClientResponse adviseCall(
            @NonNull ChatClientRequest chatClientRequest,
            CallAdvisorChain chain) {

        long start = System.nanoTime();
        log.info(
                "LLM_REQUEST requestId={} advisor={}",
                CorrelationContext.getRequestId(),
                getName()
        );
        ChatClientResponse response = chain.nextCall(chatClientRequest);

        if (response.chatResponse() != null && response.chatResponse().getResult() != null) {

            log.info(  "LLM_RESPONSE requestId={} response={}",
                    CorrelationContext.getRequestId(),
                    response.chatResponse()
                            .getResult()
                            .getOutput()
                            .getText()
            );
        } else {

            log.info("""
                    =====================================================
                    MCP / TOOL RESPONSE
                    =====================================================
                    No final LLM text available.
                    This response most likely contains a tool call.
                    =====================================================
                    """);
        }
            log.info(
                "LLM_EXECUTION requestId={} duration={}ms",
                CorrelationContext.getRequestId(),
                Duration.ofNanos(System.nanoTime() - start).toMillis()
        );

        return response;
    }

    @Override
    public String getName() {
        return getClass().getSimpleName();
    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }
}
