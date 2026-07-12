package com.rambabu.ai.rag.service;

import com.rambabu.ai.rag.config.RagProperties;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DefaultRetrievalService
        implements RetrievalService {

    private final VectorStore vectorStore;
    private final RagProperties ragProperties;

    public DefaultRetrievalService(VectorStore vectorStore, RagProperties ragProperties) {
        this.vectorStore = vectorStore;
        this.ragProperties = ragProperties;
    }
    @Override
    public List<Document> retrieveRelevantDocuments(String question) {

        SearchRequest searchRequest = SearchRequest.builder()
                .query(question)
                .topK(ragProperties.getTopK())
              //  .similarityThreshold(ragProperties.getSimilarityThreshold())
                .build();

        return vectorStore.similaritySearch(searchRequest);
    }
}
