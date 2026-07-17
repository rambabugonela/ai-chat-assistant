package com.rambabu.ai.routing.handler;

import com.rambabu.ai.dto.ChatRequest;
import com.rambabu.ai.dto.ChatResponse;
import com.rambabu.ai.routing.QueryRoute;
import org.springframework.stereotype.Component;

@Component
public class ConversationHandler implements QueryRouteHandler {

    @Override
    public QueryRoute supportedRoute() {
        return QueryRoute.CONVERSATION;
    }

    @Override
    public ChatResponse handle(String message, String sessionId) {

        throw new UnsupportedOperationException(
                "Conversation handler not implemented.");

    }

}
