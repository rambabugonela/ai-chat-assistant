package com.rambabu.ai.routing;

import org.springframework.stereotype.Component;

@Component
public class QueryRouter implements QueryClassifier {

    @Override
    public QueryRoute classify(String question) {

        String q = question.toLowerCase();

        // Conversation
        if (q.contains("previous")
                || q.contains("last question")
                || q.contains("remember")
                || q.contains("earlier")) {

            return QueryRoute.CONVERSATION;
        }

        // Hybrid
        if ((q.contains("redis")
                || q.contains("spring")
                || q.contains("java"))
                &&
                (q.contains("status")
                        || q.contains("running")
                        || q.contains("health"))) {

            return QueryRoute.HYBRID;
        }
        // MCP
        if (q.contains("database")
                || q.contains("jvm")
                || q.contains("system")
                || q.contains("environment")
                || q.contains("git")
                || q.contains("application")) {

            return QueryRoute.MCP;
        }



        return QueryRoute.RAG;
    }
}
