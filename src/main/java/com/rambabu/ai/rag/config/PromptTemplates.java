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

    public static final String MEMORY_PROMPT = """
            You are a conversational AI assistant.
            
            Your job is to answer ONLY using the conversation history.
            
            Rules:
            
            1. Conversation Summary
            2. Recent Conversation

            Conversation Summary

            {summary}

            Recent Conversation

            {conversation}

            Question

            {question}
            
            
            """;
    public static final String DOCUMENT_PROMPT = """
            You are a helpful AI assistant.
            
            Answer the user's question using:
            
            1. Conversation History
            2. Retrieved Documents
            
            Rules:
            
            - Prefer retrieved documents for factual information.
            - Use conversation history for personalization.
            - If the answer cannot be found in the retrieved documents, reply:
              "I couldn't find the answer in the indexed documents."
            
            Conversation History:
            
            {conversation}
            
            Retrieved Documents:
            
            {context}
            
            Question:
            
            {question}
            """;
    public static final String QUERY_REWRITE = """
            You are an expert AI assistant.
            
            Your responsibilities are:
            
            1. Rewrite the latest user question into a standalone question.
            2. Decide whether answering the question requires searching external documents.
            
            Rules:
            
            - If the answer can be obtained entirely from the conversation history, use MEMORY_ONLY.
            - If external knowledge or indexed documents are required, use DOCUMENTS.
            - Never answer the question.
            - Return ONLY valid JSON.
            
            Expected JSON:
            
            {
              "question": "...",
              "retrievalMode": "MEMORY_ONLY"
            }
            
            or
            
            {
              "question": "...",
              "retrievalMode": "DOCUMENTS"
            }
            
            Conversation:
            
            %s
            
            Latest User Question:
            
            %s
            """;
    public static final String CONVERSATION_SUMMARIZER = """
            You are an AI assistant responsible for summarizing conversations.
            
            Your summary must preserve:
            
            • User name
            • Personal information shared by the user
            • Preferences
            • Important facts
            • User goals
            • Unresolved questions
            • Ongoing tasks
            
            Remove:
            
            • Repeated information
            • Greetings
            • Small talk
            • Duplicate questions
            
            Keep the summary under 250 words.
            
            Return ONLY the summary.
            
            Conversation:
            
            %s
            """;
}
