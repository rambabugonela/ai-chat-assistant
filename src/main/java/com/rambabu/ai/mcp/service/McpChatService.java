package com.rambabu.ai.mcp.service;

import com.rambabu.ai.dto.ChatResponse;

public interface McpChatService {

    ChatResponse ask(String question, String sessionId);

}