package com.rambabu.ai.service;

import com.rambabu.ai.dto.ChatResponse;
import com.rambabu.ai.prompt.PromptProvider;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ChatServiceImpl implements ChatService{

    final private ChatClient chatClient;
    final private PromptProvider promptProvider;
    @Value("${spring.ai.google.genai.chat.options.model}")
    private String modelName;
    public ChatServiceImpl(ChatClient.Builder builder, PromptProvider promptProvider) {
        this.chatClient = builder.build();
        this.promptProvider = promptProvider;
    }

    @Override
    public ChatResponse chat(String message) {
        long start = System.currentTimeMillis();

        String response = chatClient
                .prompt()
                .system(promptProvider.getSystemPrompt("javaArchitect"))
                .user(message)
                .call()
                .content();

        long end = System.currentTimeMillis();


        return new ChatResponse(
                response,
                modelName,
                LocalDateTime.now(),
                end - start
        );
    }
}
