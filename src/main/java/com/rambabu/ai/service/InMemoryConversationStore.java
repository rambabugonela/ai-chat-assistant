package com.rambabu.ai.service;

import com.rambabu.ai.memory.Conversation;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Repository
@Profile("dev")
public class InMemoryConversationStore implements ConversationStore{
    private final Map<String, Conversation> inMemoryMap = new ConcurrentHashMap<>();

    @Override
    public Optional<Conversation> getConversation(String sessionId) {
        return Optional.ofNullable(inMemoryMap.get(sessionId));
    }

    @Override
    public void saveConversation(Conversation conversation) {
        inMemoryMap.put(conversation.getSessionId(),conversation);
    }

    @Override
    public void deleteConversation(String sessionId) {
        inMemoryMap.remove(sessionId);
    }
}
