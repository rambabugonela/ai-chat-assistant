package com.rambabu.ai.routing;

public interface IntentClassifier {

    RoutingDecision classify(String question);

}