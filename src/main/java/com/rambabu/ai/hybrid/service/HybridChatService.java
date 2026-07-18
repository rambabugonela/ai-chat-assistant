package com.rambabu.ai.hybrid.service;

import com.rambabu.ai.dto.ChatResponse;

public interface HybridChatService {

    ChatResponse ask(String question, String sessionId);

}