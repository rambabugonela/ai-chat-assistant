package com.rambabu.ai.rag.repository;

import com.rambabu.ai.rag.model.DocumentRegistry;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DocumentRegistryRepository extends JpaRepository<DocumentRegistry, Long> {
    @Override
    List<DocumentRegistry> findAll();
}
