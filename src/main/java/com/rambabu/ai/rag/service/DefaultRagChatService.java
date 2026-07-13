package com.rambabu.ai.rag.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.rambabu.ai.exception.AiServiceException;
import com.rambabu.ai.exception.ErrorCode;
import com.rambabu.ai.memory.Conversation;
import com.rambabu.ai.prompt.PromptType;
import com.rambabu.ai.service.ConversationStore;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
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
    private final ConversationStore conversationStore;
    private final QueryRewriter queryRewriter;
    @Value("${spring.ai.google.genai.chat.options.model}")
    private String model;

    @Override
    public ChatResponse ask(String question,  String sessionId) {
        long start = System.currentTimeMillis();
        Conversation conversation =
                loadOrCreateConversation(sessionId);
        String rewrittenQuestion =
                queryRewriter.rewrite(
                        question,
                        conversation
                );
        List<Document> documents =
                retrievalService.retrieveRelevantDocuments(rewrittenQuestion);
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
                promptBuilder.buildPrompt(rewrittenQuestion, documents, conversation);

        String llmResponse =
                chatModel.call(prompt).getResult().getOutput().getText();
        List<String> sources = documents.stream()
                .map(document -> (String) document.getMetadata().get("file_name"))
                .distinct()
                .toList();
        long end = System.currentTimeMillis();
       ChatResponse chatResponse =  new ChatResponse(
               llmResponse,
                model,
                Instant.now(),
                end-start,
               sources
        );
        conversation.addUserMessage(question);
        conversation.addAssistantMessage(chatResponse.response());
        conversationStore.saveConversation(conversation);
        return chatResponse;
    }

    private @NonNull Conversation loadOrCreateConversation(String sessionId) {
        return conversationStore.getConversation(sessionId)
                .orElseGet(() -> new Conversation(sessionId));
    }
}
