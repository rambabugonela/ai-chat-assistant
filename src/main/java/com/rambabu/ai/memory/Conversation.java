package com.rambabu.ai.memory;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Conversation {
    @Getter
    private final String sessionId;
    private final List<Message> messages;
    @Getter
    ConversationSummary summary;
    @Getter
    private Instant lastAccessed;

    public Conversation(String sessionId) {
        this.sessionId = sessionId;
        this.messages = new ArrayList<>();
        this.lastAccessed = Instant.now();
        this.summary = null;
    }

    public void addUserMessage(String message){
      addMessage(MessageRole.USER, message);
    }

    public void addSystemMessage(String message){
        addMessage(MessageRole.SYSTEM, message);
    }
    private void addMessage(MessageRole user, String message) {
        messages.add(new Message(user, message, Instant.now()));
        lastAccessed = Instant.now();
    }

    public void addAssistantMessage(String message){
        addMessage(MessageRole.ASSISTANT, message);
    }
    public List<Message> getMessages(){
        return Collections.unmodifiableList(messages);
    }

    public boolean requiresSummarization(int threshold) {
        return messages.size() >= threshold;
    }




    public String asTranscript() {
        return messages.stream()
                .map(message ->
                        "%s: %s".formatted(
                                message.role(),
                                message.content()))
                .collect(Collectors.joining(System.lineSeparator()));
    }

    public String asSummaryInput() {
        return messages.stream()
                .map(m -> m.role() + ": " + m.content())
                .collect(Collectors.joining(System.lineSeparator()));
    }

    public void updateSummary(ConversationSummary summary) {
        if (summary == null) {
            throw new IllegalArgumentException(
                    "Conversation summary cannot be null.");
        }
        this.summary = summary;
    }

    public void compress(int keepLastMessages) {
        if (keepLastMessages <= 0) {
            throw new IllegalArgumentException(
                    "keepLastMessages must be greater than zero.");
        }
        if (messages.size() <= keepLastMessages) {
            return;
        }
        int startIndex = messages.size() - keepLastMessages;
        List<Message> recentMessages =
                new ArrayList<>(messages.subList(startIndex, messages.size()));
        messages.clear();
        messages.addAll(recentMessages);
    }

    public String summaryText() {

        return summary == null
                ? ""
                : summary.content();
    }
}
