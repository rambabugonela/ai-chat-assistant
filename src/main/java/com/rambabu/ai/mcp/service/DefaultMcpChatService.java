package com.rambabu.ai.mcp.service;

import com.rambabu.ai.dto.ChatResponse;
import com.rambabu.ai.observability.CorrelationContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class DefaultMcpChatService implements McpChatService {

    private final ChatClient chatClient;

    @Value("${spring.ai.google.genai.chat.options.model}")
    private String modelName;

    @Override
    public ChatResponse ask(String question,
                            String sessionId) {

        long start = System.currentTimeMillis();
        log.info("MCP REQUEST STARTED requestId={} SessionId={} Model={} Question={}",
                CorrelationContext.getRequestId(),
                sessionId,
                modelName,
                question);

        String response = chatClient.prompt()
                .user(question)
                .call()
                .content();
        long end = System.currentTimeMillis();
        log.info( "MCP REQUEST COMPLETED requestId={} Model={} Duration={}ms Status=SUCCESS",
                CorrelationContext.getRequestId(),
                modelName,
                end - start);
        return new ChatResponse(
                response,
                modelName,
                Instant.now(),
                end - start,
                List.of());

    }
}