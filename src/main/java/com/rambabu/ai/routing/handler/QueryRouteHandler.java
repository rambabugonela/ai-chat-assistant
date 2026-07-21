package com.rambabu.ai.routing.handler;

import com.rambabu.ai.dto.ChatResponse;
import com.rambabu.ai.routing.QueryRoute;

public interface QueryRouteHandler {

    QueryRoute supportedRoute();

    public ChatResponse handle(String message, String sessionId);


    }