package com.rambabu.ai.routing.handler;

import com.rambabu.ai.dto.ChatResponse;
import com.rambabu.ai.routing.QueryRoute;
import org.springframework.stereotype.Component;

@Component
public class McpHandler implements QueryRouteHandler {

    @Override
    public QueryRoute supportedRoute() {
        return QueryRoute.MCP;
    }

    @Override
    public ChatResponse handle(String message,  String sessionId) {
        throw new UnsupportedOperationException(
                "MCP handler not implemented.");

    }

}
