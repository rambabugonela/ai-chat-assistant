package com.rambabu.ai.routing.llm;

import com.rambabu.ai.routing.IntentClassifier;
import com.rambabu.ai.routing.RoutingDecision;
import com.rambabu.ai.routing.llm.dto.LlmRoutingResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Primary
@Service
@RequiredArgsConstructor
public class LlmIntentClassifier implements IntentClassifier {

    private final IntentInferenceService inferenceService;
    private final IntentResponseValidator validator;

    @Override
    public RoutingDecision classify(String question) {

        LlmRoutingResponse response =
                inferenceService.infer(question);

        return validator.validate(response);

    }

}
