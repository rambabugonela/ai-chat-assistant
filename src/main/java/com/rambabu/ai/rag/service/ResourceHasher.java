package com.rambabu.ai.rag.service;

import org.springframework.core.io.Resource;

public interface ResourceHasher {

    String calculateHash(Resource resource);

}
