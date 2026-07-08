package com.rambabu.ai.prompt;

import org.checkerframework.checker.nullness.qual.MonotonicNonNull;
import org.springframework.stereotype.Component;

@Component
public class PromptProvider {

    private final PromptRegistry promptRegistry;

    public PromptProvider(PromptRegistry promptRegistry){
        this.promptRegistry = promptRegistry;
    }
    public String getSystemPrompt(@MonotonicNonNull PromptType promptType){

        String prompt = promptRegistry.getPrompt(promptType.getFileName());
        if(prompt == null || prompt.isEmpty()) {
            throw  new IllegalArgumentException("Unable to load Prompt file" + promptType.getFileName());
        }
        return prompt;

    }
}
