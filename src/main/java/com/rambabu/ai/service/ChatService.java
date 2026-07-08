package com.rambabu.ai.service;

import com.rambabu.ai.dto.ChatResponse;

public interface ChatService {

    ChatResponse chat(String message);
}
