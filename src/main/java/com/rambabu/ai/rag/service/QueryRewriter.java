package com.rambabu.ai.rag.service;


import com.rambabu.ai.memory.Conversation;

public interface QueryRewriter {

    /**
     * Rewrites the user's latest question into a standalone question
     * using the conversation history.
     */
    String rewrite(String question, Conversation conversation);

}