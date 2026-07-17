package com.rambabu.ai.controller;

import com.rambabu.ai.dto.ChatRequest;
import com.rambabu.ai.dto.ChatResponse;
import com.rambabu.ai.dto.RagChatRequest;
import com.rambabu.ai.rag.service.RagChatService;
import com.rambabu.ai.routing.RoutingService;
import com.rambabu.ai.service.ChatService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ChatController {

    final private ChatService chatService;
    final private RagChatService ragChatService;
    final private RoutingService routingService;

    public ChatController(ChatService chatService, RagChatService ragChatService, RoutingService routingService) {
        this.chatService = chatService;
        this.ragChatService = ragChatService;
        this.routingService = routingService;
    }
    @PostMapping("/chat")
    public ChatResponse chat(@Valid @RequestBody ChatRequest request){
        return chatService.chat(request.message(), request.promptType(), request.sessionId());
    }

    @PostMapping("/rag/chat")
    public ChatResponse ask(
            @Valid @RequestBody RagChatRequest request) {
      //  return ragChatService.ask(request.message(), request.sessionId());
        return routingService.route(request.message(), request.sessionId());
    }
}
