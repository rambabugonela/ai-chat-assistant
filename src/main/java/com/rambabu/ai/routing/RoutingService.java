package com.rambabu.ai.routing;

import com.rambabu.ai.advisor.LoggingAdvisor;
import com.rambabu.ai.dto.ChatRequest;
import com.rambabu.ai.dto.ChatResponse;
import com.rambabu.ai.routing.handler.QueryRouteHandler;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class RoutingService {

    private final QueryRouter queryRouter;

    private final Map<QueryRoute, QueryRouteHandler> handlerMap;
    private static final Logger log =
            LoggerFactory.getLogger(RoutingService.class);
    public RoutingService(QueryRouter queryRouter,
                          List<QueryRouteHandler> handlers) {

        this.queryRouter = queryRouter;

        this.handlerMap = handlers.stream()
                .collect(Collectors.toMap(
                        QueryRouteHandler::supportedRoute,
                        Function.identity()));
    }

    public ChatResponse route(String message, String sessionId) {

        QueryRoute route = queryRouter.classify(message);

        log.info("Selected Route : {}", route);

        return handlerMap.get(route).handle(message, sessionId);
    }
}
