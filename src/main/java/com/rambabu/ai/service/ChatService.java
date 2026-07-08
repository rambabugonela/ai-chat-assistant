package com.rambabu.ai.service;

import com.rambabu.ai.dto.ChatResponse;
import com.rambabu.ai.prompt.PromptType;

public interface ChatService {

    ChatResponse chat(String message, PromptType promptType);
}
