package com.rambabu.ai.rag.service;

import com.rambabu.ai.dto.ChatResponse;

public interface RagChatService {
    ChatResponse ask(String question);
}
