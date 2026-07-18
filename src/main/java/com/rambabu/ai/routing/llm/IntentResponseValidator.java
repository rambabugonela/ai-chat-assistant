package com.rambabu.ai.routing.llm;

import com.rambabu.ai.routing.QueryRoute;
import com.rambabu.ai.routing.RoutingDecision;
import com.rambabu.ai.routing.llm.dto.LlmRoutingResponse;
import org.springframework.stereotype.Component;

@Component
public class IntentResponseValidator {

    public RoutingDecision validate(LlmRoutingResponse response) {

        if (response == null) {
            return RoutingDecision.unknown(
                    "LLM returned null response.");
        }

        QueryRoute route;

        try {

            route = QueryRoute.valueOf(
                    response.route().toUpperCase());

        } catch (Exception ex) {

            return RoutingDecision.unknown(
                    "Unknown route returned by LLM.");

        }

        return new RoutingDecision(
                route,
                response.reason());
    }
}