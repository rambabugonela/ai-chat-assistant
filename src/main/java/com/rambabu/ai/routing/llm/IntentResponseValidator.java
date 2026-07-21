package com.rambabu.ai.routing.llm;

import com.rambabu.ai.routing.QueryRoute;
import com.rambabu.ai.routing.RoutingDecision;
import com.rambabu.ai.routing.llm.dto.LlmRoutingResponse;
import org.springframework.stereotype.Component;

@Component
public class IntentResponseValidator {

    public RoutingDecision validate(
            LlmRoutingResponse response) {

        if (response == null) {

            return new RoutingDecision(
                    QueryRoute.UNKNOWN,
                    "LLM response is null");

        }

        if (response.route() == null ||
                response.route().isBlank()) {

            return new RoutingDecision(
                    QueryRoute.UNKNOWN,
                    "Route missing");

        }

        try {

            QueryRoute route =
                    QueryRoute.valueOf(
                            response.route()
                                    .trim()
                                    .toUpperCase());

            String reason =
                    response.reason() == null
                            ? ""
                            : response.reason().trim();

            return new RoutingDecision(route,
                    reason);

        } catch (IllegalArgumentException ex) {

            return new RoutingDecision(
                    QueryRoute.UNKNOWN,
                    "Unknown route returned by LLM");

        }

    }

}