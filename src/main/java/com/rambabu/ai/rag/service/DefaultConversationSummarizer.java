package com.rambabu.ai.rag.service;

import com.rambabu.ai.memory.Conversation;
import com.rambabu.ai.memory.ConversationSummary;
import com.rambabu.ai.rag.config.PromptTemplates;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class DefaultConversationSummarizer implements ConversationSummarizer{

    private final ChatClient chatClient;



    public DefaultConversationSummarizer(ChatClient.Builder builder) {
        this.chatClient = builder.build();
    }
    @Override
    public ConversationSummary summarize(Conversation conversation) {
        String prompt =
                PromptTemplates.CONVERSATION_SUMMARIZER
                        .formatted(
                                conversation.asTranscript());
        String summary =
                chatClient
                        .prompt(prompt)
                        .call()
                        .content();
        return new ConversationSummary(
                summary,
                Instant.now()
        );
    }

}
