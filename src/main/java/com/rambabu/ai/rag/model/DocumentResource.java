package com.rambabu.ai.rag.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import org.springframework.ai.document.Document;
import org.springframework.core.io.Resource;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class DocumentResource {

    private Resource resource;
    private final String resourcePath;
    private List<Document> documents;
}
