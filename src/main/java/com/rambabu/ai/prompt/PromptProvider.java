package com.rambabu.ai.prompt;

import org.springframework.stereotype.Component;

@Component
public class PromptProvider {

    public String getSystemPrompt(String promptType){

       if(promptType.equals("javaArchitect"))
        return """
                You are a Senior Java Architect with 20 years of experience.

                Rules:
                1. Answer professionally.
                2. Explain concepts with enterprise examples.
                3. Follow SOLID principles.
                4. Prefer Java 21 features.
                5. Keep answers concise.
                6. If you don't know something, clearly say so.
                """;
    }
}
