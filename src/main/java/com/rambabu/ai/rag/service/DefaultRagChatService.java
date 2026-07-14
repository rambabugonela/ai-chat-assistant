package com.rambabu.ai.rag.service;

import com.rambabu.ai.dto.ChatResponse;
import com.rambabu.ai.memory.Conversation;
import com.rambabu.ai.memory.ConversationSummary;
import com.rambabu.ai.rag.model.RetrievalMode;
import com.rambabu.ai.rag.model.RewrittenQuery;
import com.rambabu.ai.service.ConversationStore;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.ai.chat.model.ChatModel;
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
    private final ConversationSummarizer conversationSummarizer;
    @Value("${conversation.summary.threshold}")
    private int summaryThreshold;
    @Value("${conversation.summary.keep-recent-messages}")
    private int keepRecentMessages;

    @Override
    public ChatResponse ask(String question,  String sessionId) {
        long start = System.currentTimeMillis();
        Conversation conversation =
                loadOrCreateConversation(sessionId);
        RewrittenQuery rewrittenQuery =
                queryRewriter.rewrite(question, conversation);

        List<Document> documents =
                rewrittenQuery.retrievalMode() == RetrievalMode.DOCUMENTS
                        ? retrievalService.retrieveRelevantDocuments(rewrittenQuery.question())
                        : List.of();
        if (rewrittenQuery.retrievalMode().equals(RetrievalMode.DOCUMENTS) && documents.isEmpty()) {
            return new ChatResponse(
                    "I couldn't find any relevant information...",
                    model,
                    Instant.now(),
                    System.currentTimeMillis() - start,
                    List.of()
            );
        }
        Prompt prompt =
                promptBuilder.buildPrompt(rewrittenQuery.question(), documents, conversation);
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
        if (conversation.requiresSummarization(summaryThreshold)) {
            ConversationSummary summary =
                    conversationSummarizer.summarize(conversation);
            conversation.updateSummary(summary);
            conversation.compress(keepRecentMessages);
        }
        conversationStore.saveConversation(conversation);
        return chatResponse;
    }

    private @NonNull Conversation loadOrCreateConversation(String sessionId) {
        return conversationStore.getConversation(sessionId)
                .orElseGet(() -> new Conversation(sessionId));
    }
}
