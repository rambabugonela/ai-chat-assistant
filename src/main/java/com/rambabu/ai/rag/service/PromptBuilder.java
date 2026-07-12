package com.rambabu.ai.rag.service;

import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.document.Document;

import java.util.List;

public interface PromptBuilder {

    Prompt buildPrompt(String question,
                       List<Document> documents);

}