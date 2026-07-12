package com.rambabu.ai.rag.loader;

import com.rambabu.ai.exception.AiServiceException;
import com.rambabu.ai.exception.ErrorCode;
import com.rambabu.ai.rag.config.RagProperties;
import com.rambabu.ai.rag.model.DocumentResource;
import org.springframework.ai.reader.pdf.PagePdfDocumentReader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class PdfDocumentLoader implements DocumentLoader {

    private final RagProperties ragProperties;
    private final ResourcePatternResolver resourceResolver;
    String documentLocation;
    public PdfDocumentLoader(RagProperties ragProperties,
                             ResourcePatternResolver resourceResolver) {
        this.ragProperties = ragProperties;
        this.resourceResolver = resourceResolver;
    }

    @Override
    public List<DocumentResource> loadDocuments() {
        documentLocation = ragProperties.getDocumentsLocation();
        List<DocumentResource> documentResources;

        try {

            Resource[] resources = resourceResolver.getResources(documentLocation);
            if (resources.length == 0) {
                throw new AiServiceException(
                        ErrorCode.INTERNAL_SERVER_ERROR,
                        "No PDF documents found in "
                                + documentLocation);
            }
            documentResources = new ArrayList<>();
            Arrays.stream(resources).
                    filter(f->f.getFilename().endsWith(".pdf")).map(resource ->
                    new DocumentResource(resource, resource.getDescription(), new PagePdfDocumentReader(resource).get()))
                    .forEach(documentResources::add);

        } catch (Exception e) {
            throw new AiServiceException(ErrorCode.INTERNAL_SERVER_ERROR,
                    "Failed to load PDF documents from location: "
                    + documentLocation);
        }
        return documentResources;
    }
}
