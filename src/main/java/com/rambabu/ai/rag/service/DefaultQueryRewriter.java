package com.rambabu.ai.rag.service;

import com.rambabu.ai.memory.Conversation;
import com.rambabu.ai.rag.config.PromptTemplates;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class DefaultQueryRewriter implements QueryRewriter {

    private final ChatClient chatClient;

    public DefaultQueryRewriter(ChatClient.Builder builder) {
        this.chatClient = builder.build();
    }


    @Override
    public String rewrite(String question, Conversation conversation) {
        if (conversation.getMessages().size() < 2) {
            return question;
        }
        String prompt = PromptTemplates.QUERY_REWRITE
                .formatted(
                        conversation.asTranscript(),
                        question
                );

        return chatClient
                .prompt(prompt)
                .call()
                .content();   // temporary
    }

    private String buildConversationHistory(Conversation conversation) {

        return conversation.getMessages()
                .stream()
                .map(message ->
                        message.role() + ": " + message.content())
                .collect(Collectors.joining(System.lineSeparator()));
    }
}