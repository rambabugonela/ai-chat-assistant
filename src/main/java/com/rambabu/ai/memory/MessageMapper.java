package com.rambabu.ai.memory;

import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class MessageMapper {

    public List<org.springframework.ai.chat.messages.Message> toSpringMessages(Conversation conversation){
        List<org.springframework.ai.chat.messages.Message> aiMessages = new ArrayList<>();
        conversation.getMessages().forEach(message -> toSpringMessage(message, aiMessages));
        return aiMessages;
    }

    private static void toSpringMessage(Message message, List<org.springframework.ai.chat.messages.Message> aiMessages) {
        switch (message.role()) {
            case USER -> {
                aiMessages.add(new UserMessage(message.content()));
            }
            case ASSISTANT -> {
                aiMessages.add(new AssistantMessage(message.content()));
            }
            case SYSTEM -> {
                aiMessages.add(new SystemMessage(message.content()));
            }
        }
    }
}
