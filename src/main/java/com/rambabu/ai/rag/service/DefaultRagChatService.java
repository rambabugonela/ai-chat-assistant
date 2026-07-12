package com.rambabu.ai.rag.service;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.model.ChatModel;
import com.rambabu.ai.dto.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.document.Document;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DefaultRagChatService
        implements RagChatService {
    private final RetrievalService retrievalService;
    private final PromptBuilder promptBuilder;
    private final ChatModel chatModel;
    @Value("${spring.ai.google.genai.chat.options.model}")
    private String model;

    @Override
    public ChatResponse ask(String question) {
        long start = System.currentTimeMillis();
        List<Document> documents =
                retrievalService.retrieveRelevantDocuments(question);
        if (documents.isEmpty()) {
            return new ChatResponse(
                    "I couldn't find any relevant information...",
                    model,
                    Instant.now(),
                    System.currentTimeMillis() - start,
                    List.of()
            );
        }
        Prompt prompt =
                promptBuilder.buildPrompt(question, documents);

        String llmResponse =
                chatModel.call(prompt).getResult().getOutput().getText();
        List<String> sources = documents.stream()
                .map(document -> (String) document.getMetadata().get("file_name"))
                .distinct()
                .toList();
        long end = System.currentTimeMillis();
       return new ChatResponse(
               llmResponse,
                model,
                Instant.now(),
                end-start,
               sources
        );

    }
}
