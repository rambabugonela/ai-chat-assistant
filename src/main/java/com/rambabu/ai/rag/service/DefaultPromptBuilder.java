package com.rambabu.ai.rag.service;

import com.rambabu.ai.memory.Conversation;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.document.Document;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.rambabu.ai.rag.config.PromptTemplates.*;

@Service
public class DefaultPromptBuilder implements PromptBuilder {

    @Override
    public Prompt buildPrompt(String question, List<Document> documents, Conversation conversation) {
        String context = buildContext(documents);
        String conversationHistory = conversation.asTranscript();
        PromptTemplate promptTemplate;

        if (documents.isEmpty()) {
            promptTemplate = new PromptTemplate(MEMORY_PROMPT);
        } else {
            promptTemplate = new PromptTemplate(DOCUMENT_PROMPT);
        }

        Map<String, Object> variables;

        if (documents.isEmpty()) {
            variables = Map.of(
                    "conversation", conversationHistory,
                    "question", question
            );
        } else {
            variables = Map.of(
                    "conversation", conversationHistory,
                    "context", context,
                    "question", question
            );
        }

        return promptTemplate.create(variables);
    }


    private String buildContext(List<Document> documents) {

        return documents.stream()
                .map(Document::getText)
                .collect(Collectors.joining(System.lineSeparator()));
    }
}
