package com.rambabu.ai.prompt;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class PromptRegistry implements CommandLineRunner {

    private final Map<String, String> promptMap = new ConcurrentHashMap<>();

    @Override
    public void run(String... args) throws Exception {
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();

        Resource[] resources = resolver.getResources("classpath:prompts/*");

        for(Resource resource :resources) {
            String fileName = resource.getFilename();
            if(fileName!=null) {
                String content = new String(resource.getInputStream().readAllBytes(), StandardCharsets.UTF_8);
                String key = fileName.replaceAll("\\.[^.]+$","");
                promptMap.put(key, content);
            }
        }
    }

    public String getPrompt(String key) {
        return promptMap.get(key);
    }
}
