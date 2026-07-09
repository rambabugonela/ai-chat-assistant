package com.rambabu.ai.service;

import com.rambabu.ai.dto.ChatResponse;
import com.rambabu.ai.memory.Conversation;
import com.rambabu.ai.memory.MessageMapper;
import com.rambabu.ai.prompt.PromptProvider;
import com.rambabu.ai.prompt.PromptType;
import org.jspecify.annotations.NonNull;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
public class ChatServiceImpl implements ChatService {

    private final ChatClient chatClient;
    private final PromptProvider promptProvider;
    private final ConversationStore conversationStore;
    private final MessageMapper messageMapper;

    @Value("${spring.ai.google.genai.chat.options.model}")
    private String modelName;

    public ChatServiceImpl(ChatClient.Builder builder, PromptProvider promptProvider, ConversationStore conversationStore, MessageMapper messageMapper) {
        this.chatClient = builder.build();
        this.promptProvider = promptProvider;
        this.conversationStore = conversationStore;
        this.messageMapper = messageMapper;
    }

    @Override
    public ChatResponse chat(String message, PromptType promptType, String sessionId) {
        long start = System.currentTimeMillis();

        Conversation conversation = loadOrCreateConversation(message, promptType, sessionId);
        List<Message> chatMessages = messageMapper.toSpringMessages(conversation);
        conversation.addUserMessage(message);
        String response = chatClient
                .prompt(new Prompt(chatMessages))
                .call()
                .content();
        conversation.addAssistantMessage(response);
        conversationStore.saveConversation(conversation);
        long end = System.currentTimeMillis();

        return new ChatResponse(
                response,
                modelName,
                Instant.now(),
                end - start
        );
    }

    private @NonNull Conversation loadOrCreateConversation(String message, PromptType promptType, String sessionId) {
        return conversationStore.getConversation(sessionId)
                .orElseGet(() -> {
                    Conversation conv = new Conversation(sessionId);
                    conv.addSystemMessage(promptProvider.getSystemPrompt(promptType));
                    return conv;
                });
    }
}
