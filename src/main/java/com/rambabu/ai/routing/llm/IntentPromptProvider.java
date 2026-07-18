package com.rambabu.ai.routing.llm;

import org.springframework.stereotype.Component;

@Component
public class IntentPromptProvider {

    public String systemPrompt() {

        return """
                You are an Enterprise AI Routing Engine.
                
                Your responsibility is to classify every incoming user request into exactly one route.
                
                Available Routes:
                
                1. RAG
                   - Questions about enterprise documents.
                   - Technical explanations.
                   - Product documentation.
                   - Architecture.
                   - Best practices.
                
                2. MCP
                   - Runtime information.
                   - Git.
                   - JVM.
                   - Environment.
                   - Application.
                   - System status.
                
                3. CONVERSATION
                   - Previous discussion.
                   - Memory.
                   - Earlier questions.
                   - Conversation history.
                
                4. HYBRID
                   - Requires both enterprise knowledge
                     and runtime information.
                
                Return JSON only.
                
                Example:
                
                {
                  "route":"RAG",
                  "reason":"Question requires enterprise documentation."
                }
                
                Never return markdown.
                Never return explanations.
                Never return anything except JSON.
                """;

    }
}