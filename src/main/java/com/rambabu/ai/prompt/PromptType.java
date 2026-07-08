package com.rambabu.ai.prompt;

import lombok.Getter;

@Getter
public enum PromptType {
    JAVA_ARCHITECT("java-architect.md"),
    HR("hr.md"),
    BUSINESS("business.md");

    private final String fileName;
    PromptType(String fileName) {
        this.fileName = fileName;
    }

}
