package com.rambabu.ai.routing;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RuleBasedIntentClassifier implements IntentClassifier {

    private final QueryRouter queryRouter;

    @Override
    public RoutingDecision classify(String question) {

        QueryRoute route = queryRouter.classify(question);

        return new RoutingDecision(route, "Rule Based Classification");
    }
}
