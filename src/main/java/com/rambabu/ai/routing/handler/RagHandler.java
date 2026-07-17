package com.rambabu.ai.routing.handler;

import com.rambabu.ai.dto.ChatRequest;
import com.rambabu.ai.dto.ChatResponse;
import com.rambabu.ai.rag.service.RagChatService;
import com.rambabu.ai.routing.QueryRoute;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RagHandler implements QueryRouteHandler {

    private final RagChatService ragChatService;

    @Override
    public QueryRoute supportedRoute() {
        return QueryRoute.RAG;
    }

    @Override
    public ChatResponse handle(String message, String sessionId) {
        return ragChatService.ask(message, sessionId);

    }

}