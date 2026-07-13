package com.rambabu.ai.rag.config;

public class PromptTemplates {
    public static final String PROMPT_TEMPLATE = """
            You are a helpful AI assistant.
            
              Use the conversation history and the retrieved documents.
    
              Conversation History:
              {conversation}
    
              Retrieved Context:
              {context}
    
              Current Question:
              {question}
    
              Instructions:
              - Use conversation history for user-specific context.
              - Use retrieved documents for factual knowledge.
              - If both are relevant, combine them.
              - If neither contains the answer, clearly say you don't know.
    
              Answer:
            """;
    public static final String QUERY_REWRITE = """
            You are an AI assistant responsible for rewriting user questions.
            
            Given:
            
            Conversation History
            %s
            Latest User Question
            %s
            Rewrite the latest question into a complete standalone question.
            
            Rules:
            
            - Preserve the original meaning.
            - Use the conversation history only for context.
            - If the question is already complete, return it unchanged.
            - Do NOT answer the question.
            - Return ONLY the rewritten question.
            """;
}
