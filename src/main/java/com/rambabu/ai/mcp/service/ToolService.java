package com.rambabu.ai.mcp.service;

import com.rambabu.ai.mcp.model.ToolRequest;
import com.rambabu.ai.mcp.model.ToolResponse;

public interface ToolService {

    boolean supports(String toolName);

    ToolResponse execute(ToolRequest request);
}