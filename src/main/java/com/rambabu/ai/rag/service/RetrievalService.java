package com.rambabu.ai.rag.service;

import org.springframework.ai.document.Document;

import java.util.List;

public interface RetrievalService {

    List<Document> retrieveRelevantDocuments(String question);

}