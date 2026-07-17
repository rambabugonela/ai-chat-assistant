package com.rambabu.ai.mcp.service;

import com.rambabu.ai.mcp.model.ToolRequest;
import com.rambabu.ai.mcp.model.ToolResponse;
import org.springframework.stereotype.Service;

@Service
public class McpToolService implements ToolService {

    @Override
    public boolean supports(String toolName) {
        return false;
    }

    @Override
    public ToolResponse execute(ToolRequest request) {
        return new ToolResponse(
                false,
                null,
                "MCP Tool execution not implemented."
        );
    }
}
