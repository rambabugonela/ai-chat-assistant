package com.rambabu.ai.mcp.model;

public record ToolResponse(

        boolean success,

        Object result,

        String error

) {
}