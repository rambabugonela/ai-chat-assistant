package com.rambabu.ai.routing;

public record RoutingDecision(

        QueryRoute route,

        String reason

) {

    public static RoutingDecision unknown(String reason) {
        return new RoutingDecision(QueryRoute.UNKNOWN, reason);
    }
}