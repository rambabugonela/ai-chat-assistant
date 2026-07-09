package com.rambabu.ai.memory;

import lombok.Getter;
import org.jspecify.annotations.NonNull;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Conversation {
    @Getter
    private final String sessionId;
    private final List<Message> messages;
    @Getter
    private Instant lastAssessed;

    public Conversation(String sessionId) {
        this.sessionId = sessionId;
        this.messages = new ArrayList<>();
        this.lastAssessed = Instant.now();
    }

    public void addUserMessage(String message){
      addMessage(MessageRole.USER, message);
    }

    public void addSystemMessage(String message){
        addMessage(MessageRole.SYSTEM, message);
    }
    private void addMessage(MessageRole user, String message) {
        messages.add(new Message(user, message, Instant.now()));
        lastAssessed = Instant.now();
    }

    public void addAssistantMessage(String message){
        addMessage(MessageRole.ASSISTANT, message);
    }
    public List<Message> getMessages(){
        return Collections.unmodifiableList(messages);
    }

}
