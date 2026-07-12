package com.rambabu.ai.rag.service;

import com.rambabu.ai.rag.indexer.DocumentIndexer;
import com.rambabu.ai.rag.loader.DocumentLoader;
import com.rambabu.ai.rag.model.DocumentRegistry;
import com.rambabu.ai.rag.model.DocumentResource;
import com.rambabu.ai.rag.repository.DocumentRegistryRepository;
import com.rambabu.ai.rag.splitter.DocumentSplitter;
import jakarta.transaction.Transactional;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class DefaultDocumentIndexer implements DocumentIndexer {

    private final DocumentLoader documentLoader;

    private final DocumentSplitter documentSplitter;

    private final ResourceHasher resourceHasher;

    private final DocumentRegistryRepository repository;
    private final VectorStore vectorStore;

    public DefaultDocumentIndexer(DocumentLoader documentLoader,
                                  DocumentSplitter documentSplitter,
                                  ResourceHasher resourceHasher,
                                  DocumentRegistryRepository repository,
                                  VectorStore vectorStore
    ) {
        this.documentLoader = documentLoader;
        this.documentSplitter = documentSplitter;
        this.resourceHasher = resourceHasher;
        this.repository = repository;
        this.vectorStore = vectorStore;
    }


    @Transactional
    @Override
    public void indexDocuments() {
        Set<String> indexedHashes = loadIndexedHashes();

        List<DocumentRegistry> newRegistries = new ArrayList<>();

        List<DocumentResource> resources =
                documentLoader.loadDocuments();

        for (DocumentResource resource : resources) {
            String hash = resourceHasher.calculateHash(resource.getResource());
            if (indexedHashes.contains(hash)) {
                continue;
            }
            List<Document> chunks =
                    documentSplitter.split(resource.getDocuments());
            vectorStore.add(chunks);
            newRegistries.add(
                    createRegistry(
                            resource,
                            hash,
                            chunks.size()));
        }
        repository.saveAll(newRegistries);

    }

    private Set<String> loadIndexedHashes() {

        return repository.findAll()
                .stream()
                .map(DocumentRegistry::getContentHash)
                .collect(Collectors.toSet());

    }

    private DocumentRegistry createRegistry(
            DocumentResource resource,
            String contentHash,
            int chunkCount) {

        return DocumentRegistry.builder()
                .resourcePath(resource.getResourcePath())
                .fileName(resource.getResource().getFilename())
                .contentHash(contentHash)
                .chunkCount(chunkCount)
                .indexedAt(Instant.now())
                .build();
    }

}
