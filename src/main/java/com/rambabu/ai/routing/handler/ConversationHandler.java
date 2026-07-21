package com.rambabu.ai.routing.handler;

import com.rambabu.ai.conversation.service.ConversationChatService;
import com.rambabu.ai.dto.ChatResponse;
import com.rambabu.ai.routing.QueryRoute;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ConversationHandler implements QueryRouteHandler {
    private final ConversationChatService conversationChatService;
    @Override
    public QueryRoute supportedRoute() {
        return QueryRoute.CONVERSATION;
    }

    @Override
    public ChatResponse handle(String message, String sessionId) {

       return conversationChatService.ask(message,sessionId);

    }

}
