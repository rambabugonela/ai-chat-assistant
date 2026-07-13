package com.rambabu.ai.service;

import com.rambabu.ai.memory.Message;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RedisConversationMemoryService
        implements ConversationMemoryService {
    @Override
    public void addMessage(String sessionId, Message message) {

    }

    @Override
    public List<Message> getConversation(String sessionId) {
        return List.of();
    }

    @Override
    public void clearConversation(String sessionId) {

    }

    /*RedisChatMemoryRepository chatMemoryRepository;


    @Override
    public void addMessage(String sessionId, Message message) {
        chatMemoryRepository.add(sessionId, message);
    }

    @Override
    public List<Message> getConversation(String sessionId) {
        return chatMemoryRepository.get(sessionId);
    }

    @Override
    public void clearConversation(String sessionId) {
        chatMemoryRepository.clear(sessionId);
    }*/
}
