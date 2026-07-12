package com.rambabu.ai.rag.splitter;

import com.rambabu.ai.rag.config.RagProperties;
import org.springframework.ai.document.Document;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TokenDocumentSplitter implements DocumentSplitter {

    private final TokenTextSplitter splitter;
    public TokenDocumentSplitter(RagProperties properties) {
        this.splitter = new TokenTextSplitter(
                properties.getChunkSize(),
                properties.getMinChunkSizeChars(),
                properties.getMinChunkLengthToEmbed(),
                properties.getMaxNumChunks(),
                properties.isKeepSeparator()
        );
    }

    @Override
    public List<Document> split(List<Document> documents) {
        // Implementation comes next
        return splitter.split(documents);
    }
}