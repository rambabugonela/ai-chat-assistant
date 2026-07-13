package com.rambabu.ai.service;

import com.rambabu.ai.memory.Message;

import java.util.List;

public interface ConversationMemoryService {

    void addMessage(String sessionId, Message message);

    List<Message> getConversation(String sessionId);

    void clearConversation(String sessionId);
}
