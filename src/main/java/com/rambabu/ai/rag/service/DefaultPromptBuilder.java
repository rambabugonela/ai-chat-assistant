package com.rambabu.ai.rag.service;

import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.document.Document;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.rambabu.ai.rag.config.PromptTemplates.PROMPT_TEMPLATE;

@Service
public class DefaultPromptBuilder implements PromptBuilder {

    @Override
    public Prompt buildPrompt(String question, List<Document> documents) {
        String context = buildContext(documents);

        PromptTemplate promptTemplate =
                new PromptTemplate(PROMPT_TEMPLATE);

        Map<String, Object> variables =
                Map.of(
                        "context", context,
                        "question", question
                );

        return promptTemplate.create(variables);
    }


    private String buildContext(List<Document> documents) {

        return documents.stream()
                .map(Document::getText)
                .collect(Collectors.joining(System.lineSeparator()));
    }
}
