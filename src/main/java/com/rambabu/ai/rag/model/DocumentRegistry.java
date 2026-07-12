package com.rambabu.ai.rag.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Entity
@Table(name = "document_registry")
@NoArgsConstructor
@Data
@AllArgsConstructor
@Builder
public class DocumentRegistry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String resourcePath;

    @Column(nullable = false)
    private String fileName;

    @Column(nullable = false, unique = true)
    private String contentHash;

    @Column(nullable = false)
    private Integer chunkCount;

    @Column(nullable = false)
    private Instant indexedAt;

}
