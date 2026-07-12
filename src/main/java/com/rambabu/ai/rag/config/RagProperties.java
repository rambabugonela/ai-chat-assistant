package com.rambabu.ai.rag.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "rag")
@Getter
@Setter
public class RagProperties {

    private String documentsLocation;
    private int chunkSize = 800;
    private int minChunkSizeChars = 350;
    private int minChunkLengthToEmbed = 5;
    private int maxNumChunks = 10000;
    private boolean keepSeparator = true;
    private int topK = 5;
    private double similarityThreshold = 0.75;

}