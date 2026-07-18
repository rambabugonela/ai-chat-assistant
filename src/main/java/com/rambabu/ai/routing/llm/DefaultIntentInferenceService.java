package com.rambabu.ai.routing.llm;

import com.rambabu.ai.routing.llm.dto.LlmRoutingResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DefaultIntentInferenceService
        implements IntentInferenceService {

    private final ChatClient chatClient;

    private final IntentPromptProvider promptProvider;

    @Override
    public LlmRoutingResponse infer(String question) {

        throw new UnsupportedOperationException(
                "Not implemented yet.");

    }

}
