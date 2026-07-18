package com.rambabu.ai.routing.llm;

import com.rambabu.ai.exception.AiExceptionTranslator;
import com.rambabu.ai.routing.llm.dto.LlmRoutingResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.converter.BeanOutputConverter;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DefaultIntentInferenceService implements IntentInferenceService {

    private final ChatClient chatClient;
    private final IntentPromptProvider promptProvider;
    private final AiExceptionTranslator translator;

    private final BeanOutputConverter<LlmRoutingResponse> converter = new BeanOutputConverter<>(LlmRoutingResponse.class);

    @Override
    public LlmRoutingResponse infer(String question) {

        try {

            Prompt prompt = new Prompt(List.of(new SystemMessage(
                    promptProvider.systemPrompt(converter.getFormat())), new UserMessage(question)));
            String response = chatClient.prompt(prompt).call().content();
            if (response == null || response.isBlank()) {
                throw new IllegalStateException("Empty LLM response.");

            }
            return converter.convert(response);

        } catch (RuntimeException ex) {

            throw translator.translate(ex);

        }

    }

}