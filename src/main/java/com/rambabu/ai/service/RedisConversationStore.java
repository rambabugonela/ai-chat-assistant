package com.rambabu.ai.service;

import com.rambabu.ai.memory.Conversation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Optional;

@Repository
@Profile("dev")
public class RedisConversationStore implements ConversationStore{
    private final RedisTemplate<String, Conversation> redisTemplate;
    private final String KEY_PREFIX = "conversation:";
    private final Duration conversationTtl;
    public RedisConversationStore(RedisTemplate<String, Conversation> redisTemplate,
                                  @Value("${conversation.ttl}") Duration conversationTtl) {
        this.redisTemplate = redisTemplate;
        this.conversationTtl = conversationTtl;
    }

    @Override
    public Optional<Conversation> getConversation(String sessionId) {
       return Optional.ofNullable(redisTemplate.opsForValue().get(buildKey(sessionId)));
    }

    @Override
    public void saveConversation(Conversation conversation) {
        redisTemplate.opsForValue()
                .set(buildKey(conversation.getSessionId()), conversation,
                        conversationTtl);
    }

    @Override
    public void deleteConversation(String sessionId) {
        redisTemplate.delete(buildKey(sessionId));
    }

    private String buildKey(String sessionId){
        return KEY_PREFIX + sessionId;
    }
}
