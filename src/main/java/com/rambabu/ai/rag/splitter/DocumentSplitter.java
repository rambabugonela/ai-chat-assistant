package com.rambabu.ai.rag.splitter;

import org.springframework.ai.document.Document;

import java.util.List;

public interface DocumentSplitter {

    List<Document> split(List<Document> documents);

}