package com.rambabu.ai.controller;

import com.rambabu.ai.dto.ChatResponse;
import com.rambabu.ai.service.ChatService;
import com.rambabu.ai.dto.ChatRequest;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/chat")
public class ChatController {

    final private ChatService chatService;

    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }
    @PostMapping
    public ChatResponse chat(@Valid @RequestBody ChatRequest request){
        return chatService.chat(request.message());
    }
}
