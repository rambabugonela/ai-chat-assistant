package com.rambabu.ai.routing.handler;

import com.rambabu.ai.dto.ChatResponse;
import com.rambabu.ai.mcp.service.McpChatService;
import com.rambabu.ai.routing.QueryRoute;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class McpHandler implements QueryRouteHandler {

    private final McpChatService mcpChatService;

    @Override
    public QueryRoute supportedRoute() {
        return QueryRoute.MCP;
    }

    @Override
    public ChatResponse handle(String message,
                               String sessionId) {

        return mcpChatService.ask(message, sessionId);

    }
}