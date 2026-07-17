package com.rambabu.ai.routing;

import com.rambabu.ai.dto.ChatResponse;
import com.rambabu.ai.routing.handler.QueryRouteHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class RoutingService {

    private final IntentClassifier intentClassifier;

    private final Map<QueryRoute, QueryRouteHandler> handlerMap;
    private static final Logger log =
            LoggerFactory.getLogger(RoutingService.class);
    public RoutingService(IntentClassifier intentClassifier,
                          List<QueryRouteHandler> handlers) {

        this.intentClassifier = intentClassifier;

        handlers.forEach(h ->
                log.info("Registered Handler : {}", h.supportedRoute()));

        this.handlerMap = handlers.stream()
                .collect(Collectors.toMap(
                        QueryRouteHandler::supportedRoute,
                        Function.identity()));

        log.info("Handler Count : {}", handlerMap.size());
    }

    public ChatResponse route(String message, String sessionId) {

        RoutingDecision decision =
                intentClassifier.classify(message);
        log.info("Selected Route : {}, Reason : {}",
                decision.route(),
                decision.reason());

        log.info("Available Routes : {}", handlerMap.keySet());
        log.info("Requested Route  : {}", decision.route());

        QueryRouteHandler handler = handlerMap.get(decision.route());

        log.info("Handler : {}", handler);
        if (handler == null) {
            throw new IllegalStateException(
                    "No handler registered for route : " + decision.route());
        }

        return handler.handle(message, sessionId);
    }
}
