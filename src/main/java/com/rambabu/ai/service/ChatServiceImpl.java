package com.rambabu.ai.service;

import com.rambabu.ai.dto.ChatResponse;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ChatServiceImpl implements ChatService{

    final private ChatClient chatClient;

    @Value("${spring.ai.google.genai.chat.options.model}")
    private String modelName;
    public ChatServiceImpl(ChatClient.Builder builder) {
        this.chatClient = builder.build();
    }

    @Override
    public ChatResponse chat(String message) {
        long start = System.currentTimeMillis();

        String response = chatClient
                .prompt()
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
