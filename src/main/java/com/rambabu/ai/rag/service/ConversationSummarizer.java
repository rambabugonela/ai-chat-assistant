package com.rambabu.ai.rag.service;

import com.rambabu.ai.memory.Conversation;
import com.rambabu.ai.memory.ConversationSummary;

public interface ConversationSummarizer {

    ConversationSummary summarize(
            Conversation conversation);

}
