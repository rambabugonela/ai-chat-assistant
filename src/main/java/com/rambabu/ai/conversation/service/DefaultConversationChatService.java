package com.rambabu.ai.conversation.service;

import com.rambabu.ai.dto.ChatResponse;
import com.rambabu.ai.exception.AiExceptionTranslator;
import com.rambabu.ai.memory.Conversation;
import com.rambabu.ai.memory.ConversationSummary;
import com.rambabu.ai.rag.service.ConversationSummarizer;
import com.rambabu.ai.service.ConversationStore;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DefaultConversationChatService implements ConversationChatService {

    private final ChatClient chatClient;
    private final ConversationStore conversationStore;
    @Value("${spring.ai.google.genai.chat.options.model}")
    private String model;
    private final ConversationSummarizer conversationSummarizer;
    @Value("${conversation.summary.threshold}")
    private int summaryThreshold;
    @Value("${conversation.summary.keep-recent-messages}")
    private int keepRecentMessages;
    private final AiExceptionTranslator aiExceptionTranslator;
    @Override
    public ChatResponse ask(String question, String sessionId) {
        ChatResponse chatResponse;
        try {
            long start = System.currentTimeMillis();
            Conversation conversation = loadOrCreateConversation(sessionId);
            String llmResponse =
                    chatClient.prompt().user(question).call().content();
            long end = System.currentTimeMillis();
            chatResponse = new ChatResponse(
                    llmResponse,
                    model,
                    Instant.now(),
                    end - start,
                    List.of()
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
        }catch (RuntimeException  e){
           throw  aiExceptionTranslator.translate(e);
        }
        return chatResponse;

        }

    private @NonNull Conversation loadOrCreateConversation(String sessionId) {
        return conversationStore.getConversation(sessionId)
                .orElseGet(() -> new Conversation(sessionId));
    }
}
