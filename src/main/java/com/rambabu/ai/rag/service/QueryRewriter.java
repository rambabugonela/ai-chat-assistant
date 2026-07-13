package com.rambabu.ai.rag.service;


import com.rambabu.ai.memory.Conversation;
import com.rambabu.ai.rag.model.RewrittenQuery;

public interface QueryRewriter {

    /**
     * Rewrites the user's latest question into a standalone question
     * using the conversation history.
     */
    RewrittenQuery rewrite(String question, Conversation conversation);

}