package com.rambabu.ai.routing.handler;

import com.rambabu.ai.dto.ChatResponse;
import com.rambabu.ai.hybrid.service.HybridChatService;
import com.rambabu.ai.routing.QueryRoute;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class HybridHandler implements QueryRouteHandler {

    private final HybridChatService hybridChatService;

    @Override
    public QueryRoute supportedRoute() {
        return QueryRoute.HYBRID;
    }

    @Override
    public ChatResponse handle(String message,
                               String sessionId) {
        return hybridChatService.ask(message, sessionId);

    }

}
