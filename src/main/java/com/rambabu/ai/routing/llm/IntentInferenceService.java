package com.rambabu.ai.routing.llm;

import com.rambabu.ai.routing.llm.dto.LlmRoutingResponse;

public interface IntentInferenceService {

    LlmRoutingResponse infer(String question);

}