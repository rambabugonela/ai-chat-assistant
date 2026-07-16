package com.rambabu.ai.mcp.model;

import java.util.Map;

public record ToolRequest(
        String toolName,
        Map<String, Object> arguments
) {
}
