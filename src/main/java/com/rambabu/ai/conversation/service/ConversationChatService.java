package com.rambabu.ai.conversation.service;

import com.rambabu.ai.dto.ChatResponse;

public interface ConversationChatService {
    ChatResponse ask(String question, String sessionId);

}
