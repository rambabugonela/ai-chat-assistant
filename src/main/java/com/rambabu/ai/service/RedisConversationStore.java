package com.rambabu.ai.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rambabu.ai.exception.AiServiceException;
import com.rambabu.ai.exception.ErrorCode;
import com.rambabu.ai.memory.Conversation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.time.Duration;
import java.util.Optional;

@Repository
@Profile("dev")
public class RedisConversationStore implements ConversationStore {
    private final RedisTemplate<String, String> redisTemplate;
    private final String KEY_PREFIX = "conversation:";
    private final Duration conversationTtl;
    private final ObjectMapper objectMapper;

    public RedisConversationStore(RedisTemplate<String, String> redisTemplate,
                                  @Value("${conversation.ttl}") Duration conversationTtl, ObjectMapper objectMapper) {
        this.redisTemplate = redisTemplate;
        this.conversationTtl = conversationTtl;
        this.objectMapper = objectMapper;
    }

    @Override
    public Optional<Conversation> getConversation(String sessionId) {
        Conversation conversation = null;
        String json = redisTemplate.opsForValue().get(buildKey(sessionId));
        if (json == null) {
            return Optional.empty();
        }
        try {
            conversation =
                    objectMapper.readValue(json, Conversation.class);
        } catch (JsonProcessingException e) {
            throw new AiServiceException(ErrorCode.INTERNAL_SERVER_ERROR, e.getMessage());
        }
        return Optional.ofNullable(conversation);
    }


    public void saveConversation(Conversation conversation) {
        String json = null;
        try {
            json = objectMapper.writeValueAsString(conversation);
            redisTemplate.opsForValue()
                    .set(buildKey(conversation.getSessionId()), json, conversationTtl);
        } catch (JsonProcessingException e) {
            throw new AiServiceException(ErrorCode.INTERNAL_SERVER_ERROR,e);
        }

    }

    @Override
    public void deleteConversation(String sessionId) {
        redisTemplate.delete(buildKey(sessionId));
    }

    private String buildKey(String sessionId) {
        return KEY_PREFIX + sessionId;
    }

}
