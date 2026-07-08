package com.rambabu.ai.service;

import com.rambabu.ai.dto.ChatResponse;
import com.rambabu.ai.prompt.PromptProvider;
import com.rambabu.ai.prompt.PromptType;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;

@Service
public class ChatServiceImpl implements ChatService{

    private final ChatClient chatClient;
    private final PromptProvider promptProvider;
    @Value("${spring.ai.google.genai.chat.options.model}")
    private String modelName;
    public ChatServiceImpl(ChatClient.Builder builder, PromptProvider promptProvider) {
        this.chatClient = builder.build();
        this.promptProvider = promptProvider;
    }

    @Override
    public ChatResponse chat(String message, PromptType promptType) {
        long startTime = System.currentTimeMillis();

        String response = chatClient
                .prompt()
                .system(promptProvider.getSystemPrompt(promptType))
                .user(message)
                .call()
                .content();

        long endTime = System.currentTimeMillis();


        return new ChatResponse(
                response,
                modelName,
                Instant.now(),
                endTime - startTime
        );
    }
}
