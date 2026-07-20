package com.rambabu.ai.routing;

import com.rambabu.ai.dto.ChatResponse;
import com.rambabu.ai.observability.CorrelationContext;
import com.rambabu.ai.routing.handler.QueryRouteHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Slf4j
public class RoutingService {

    private final IntentClassifier intentClassifier;
    private final QueryRouter queryRouter;
    private final Map<QueryRoute, QueryRouteHandler> handlerMap;

    public RoutingService(IntentClassifier intentClassifier,
                          QueryRouter queryRouter,
                          List<QueryRouteHandler> handlers) {

        this.intentClassifier = intentClassifier;
        this.queryRouter = queryRouter;

        this.handlerMap = handlers.stream()
                .collect(Collectors.toMap(
                        QueryRouteHandler::supportedRoute,
                        Function.identity()));

        handlers.forEach(h ->
                log.info("Registered Handler : {}", h.supportedRoute()));
    }

    public ChatResponse route(String message,
                              String sessionId) {
        RoutingDecision decision;
        try {
            decision = intentClassifier.classify(message);
            if (decision.route() == QueryRoute.UNKNOWN) {
                QueryRoute fallback =
                        queryRouter.classify(message);
                decision = new RoutingDecision(
                        fallback,
                        "Rule based fallback");
            }
        } catch (RuntimeException ex) {
            log.warn("LLM routing failed. Using rule based router.", ex);
            QueryRoute fallback = queryRouter.classify(message);
            decision = new RoutingDecision(fallback, "Rule based fallback");
        }

        log.info( "AI ROUTING STARTED RequestId={} SessionId={} Question={} Route={} rease={}",
                CorrelationContext.getRequestId(),
                sessionId,
                message,
                decision.route(),
                decision.reason());
        QueryRouteHandler handler = handlerMap.get(decision.route());

        if (handler == null) {
            throw new IllegalStateException("No handler registered for " + decision.route());
        }
        return handler.handle(message, sessionId);

    }

}