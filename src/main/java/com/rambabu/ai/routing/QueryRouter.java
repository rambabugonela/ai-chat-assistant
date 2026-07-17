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
                || q.contains("earlier")
                || q.contains("remember")) {

            return QueryRoute.CONVERSATION;
        }

        // MCP
        if (q.contains("database")
                || q.contains("application")
                || q.contains("environment")
                || q.contains("git")
                || q.contains("jvm")
                || q.contains("system")) {

            return QueryRoute.MCP;
        }

        // Hybrid

        if ((q.contains("redis")
                || q.contains("spring")
                || q.contains("java"))
                &&
                (q.contains("running")
                        || q.contains("status")
                        || q.contains("health"))) {

            return QueryRoute.HYBRID;
        }

        return QueryRoute.RAG;

    }

}