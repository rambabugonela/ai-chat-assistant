package com.rambabu.ai.config;

import com.rambabu.ai.advisor.LoggingAdvisor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;

@Configuration
public class ChatClientConfig {

    @Bean
    ChatClient chatClient(ChatModel chatModel,
                          LoggingAdvisor loggingAdvisor,
                          List<ToolCallbackProvider> providers) {

        return ChatClient.builder(chatModel)

                // Existing Advisor
                .defaultAdvisors(loggingAdvisor)

                // MCP Tool Callbacks
                .defaultToolCallbacks(
                        providers.stream()
                                .flatMap(provider -> Arrays.stream(provider.getToolCallbacks()))
                                .toList())

                .build();
    }
}