package com.rambabu.ai.hybrid.service;

import com.rambabu.ai.dto.ChatResponse;
import com.rambabu.ai.rag.service.RagChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DefaultHybridChatService implements HybridChatService {

    private final RagChatService ragChatService;

    @Override
    public ChatResponse ask(String question,
                            String sessionId) {

        return ragChatService.ask(question, sessionId);

    }

}