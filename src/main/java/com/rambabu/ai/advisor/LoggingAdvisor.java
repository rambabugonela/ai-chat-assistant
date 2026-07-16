package com.rambabu.ai.advisor;

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

        long start = System.currentTimeMillis();

            log.info("""
                =====================================================
                LLM REQUEST
                =====================================================
                Advisor        : {}
                User Message   : {}
                Prompt Length  : {} chars
                =====================================================
                """,
            getName(),
            chatClientRequest.prompt().getUserMessage().getText(),
            chatClientRequest.prompt().getInstructions().getFirst().getText().length());
        ChatClientResponse response = chain.nextCall(chatClientRequest);

        log.info("""
                    ========== LLM RESPONSE =========="
                    Response :{}
                    =====================================
                    """, response.chatResponse().getResult().getOutput().getText());

        Duration duration =
                Duration.ofNanos(System.nanoTime() - start);
        log.info("duration : {}", duration + " ms");
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
