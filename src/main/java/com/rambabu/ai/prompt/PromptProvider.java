package com.rambabu.ai.prompt;

import org.checkerframework.checker.nullness.qual.MonotonicNonNull;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.EnumMap;
import java.util.Map;

@Component
public class PromptProvider {
    public final Map<PromptType, String> promptTypeStringMap = new EnumMap<>(PromptType.class);

    private final ResourceLoader resourceLoader;


    public PromptProvider(ResourceLoader resourceLoader){
        this.resourceLoader = resourceLoader;
    }
    public String getSystemPrompt(@MonotonicNonNull PromptType promptType){

        Resource resource = resourceLoader.getResource("classpath:prompts/"+promptType.getFileName());

        try(InputStream inputStream = resource.getInputStream()) {

            return new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw  new IllegalArgumentException("Prompt not defined" + promptType);
        }


    }
}
