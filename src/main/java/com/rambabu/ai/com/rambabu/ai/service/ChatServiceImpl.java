package com.rambabu.ai.com.rambabu.ai.service;

import com.rambabu.ai.com.rambabu.ai.dto.ChatResponse;
import org.springframework.stereotype.Service;

@Service
public class ChatServiceImpl implements ChatService{
    @Override
    public ChatResponse chat(String message) {
        return new ChatResponse("Hello you asked -" +message);
    }
}
