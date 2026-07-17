package com.rambabu.ai.mcp.service;

import com.rambabu.ai.dto.ChatResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DefaultMcpChatService implements McpChatService {

    private final ChatClient chatClient;

    @Value("${spring.ai.google.genai.chat.options.model}")
    private String modelName;

    @Override
    public ChatResponse ask(String question,
                            String sessionId) {

        long start = System.currentTimeMillis();

        String response = chatClient.prompt()
                .user(question)
                .call()
                .content();

        long end = System.currentTimeMillis();

        return new ChatResponse(
                response,
                modelName,
                Instant.now(),
                end - start,
                List.of());

    }
}