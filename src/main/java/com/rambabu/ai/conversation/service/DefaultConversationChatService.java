package com.rambabu.ai.conversation.service;

import com.rambabu.ai.dto.ChatResponse;
import com.rambabu.ai.exception.AiExceptionTranslator;
import com.rambabu.ai.memory.Conversation;
import com.rambabu.ai.memory.ConversationSummary;
import com.rambabu.ai.rag.service.ConversationSummarizer;
import com.rambabu.ai.rag.service.PromptBuilder;
import com.rambabu.ai.service.ConversationStore;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DefaultConversationChatService
        implements ConversationChatService {

    private final PromptBuilder promptBuilder;
    private final ChatClient chatClient;
    private final ConversationStore conversationStore;
    private final ConversationSummarizer conversationSummarizer;
    private final AiExceptionTranslator aiExceptionTranslator;

    @Value("${spring.ai.google.genai.chat.options.model}")
    private String model;

    @Value("${conversation.summary.threshold}")
    private int summaryThreshold;

    @Value("${conversation.summary.keep-recent-messages}")
    private int keepRecentMessages;

    @Override
    public ChatResponse ask(String question,
                            String sessionId) {

        try {

            long start = System.currentTimeMillis();

            Conversation conversation =
                    loadOrCreateConversation(sessionId);

            /*
             * IMPORTANT
             * Conversation does NOT retrieve documents.
             * It only passes conversation memory.
             */
            Prompt prompt =
                    promptBuilder.buildPrompt(
                            question,
                            List.of(),
                            conversation);

            String llmResponse =
                    chatClient.prompt(prompt)
                            .call()
                            .content();

            long end = System.currentTimeMillis();

            ChatResponse response =
                    new ChatResponse(
                            llmResponse,
                            model,
                            Instant.now(),
                            end - start,
                            List.of());

            conversation.addUserMessage(question);
            conversation.addAssistantMessage(response.response());

            if (conversation.requiresSummarization(summaryThreshold)) {

                ConversationSummary summary =
                        conversationSummarizer.summarize(conversation);

                conversation.updateSummary(summary);

                conversation.compress(keepRecentMessages);
            }

            conversationStore.saveConversation(conversation);

            return response;

        } catch (RuntimeException ex) {

            throw aiExceptionTranslator.translate(ex);

        }

    }

    private @NonNull Conversation loadOrCreateConversation(
            String sessionId) {

        return conversationStore.getConversation(sessionId)
                .orElseGet(() -> new Conversation(sessionId));

    }

}