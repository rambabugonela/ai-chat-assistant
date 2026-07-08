package com.rambabu.ai.prompt;

import lombok.Getter;

@Getter
public enum PromptType {
    JAVA_ARCHITECT("java-architect"),
    HR("hr"),
    BUSINESS("business");

    private final String fileName;
    PromptType(String fileName) {
        this.fileName = fileName;
    }

}
