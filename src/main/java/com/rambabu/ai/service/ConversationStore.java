package com.rambabu.ai.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.rambabu.ai.memory.Conversation;

import java.util.Optional;

public interface ConversationStore {

    Optional<Conversation> getConversation(String sessionId);
    void saveConversation(Conversation conversation);
    void deleteConversation(String sessionId);
}
