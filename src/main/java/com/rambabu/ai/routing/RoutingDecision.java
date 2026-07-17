package com.rambabu.ai.routing;

public record RoutingDecision(

        QueryRoute route,

        String reason

) {
}