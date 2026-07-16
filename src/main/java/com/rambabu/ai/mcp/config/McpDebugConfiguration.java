package com.rambabu.ai.mcp.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;

@Configuration
@Slf4j
public class McpDebugConfiguration {

    @Bean
    CommandLineRunner debugTools(List<ToolCallbackProvider> providers) {

        return args -> {

            log.info("Tool Providers : {}", providers.size());

            providers.forEach(provider -> {

                var callbacks = provider.getToolCallbacks();

                log.info("Callbacks : {}", callbacks.length);

                Arrays.stream(callbacks)
                        .forEach(callback ->
                                log.info("Tool : {}", callback.getToolDefinition().name()));

            });

        };
    }
}