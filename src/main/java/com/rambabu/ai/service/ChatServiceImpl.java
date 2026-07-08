package com.rambabu.ai.service;

import com.rambabu.ai.dto.ChatResponse;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

@Service
public class ChatServiceImpl implements ChatService{

    final private ChatClient chatClient;

    public ChatServiceImpl(ChatClient.Builder builder) {
        this.chatClient = builder.build();
    }

    @Override
    public ChatResponse chat(String message) {
        String response = chatClient.prompt().user(message).call().content();
        return new ChatResponse(response);
    }
}
