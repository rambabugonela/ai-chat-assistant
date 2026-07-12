package com.rambabu.ai;

import com.rambabu.ai.dto.ChatResponse;
import com.rambabu.ai.rag.indexer.DocumentIndexer;
import com.rambabu.ai.rag.service.RagChatService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.stereotype.Component;

@SpringBootApplication
@ConfigurationPropertiesScan
public class AiChatAssistantApplication {

	public static void main(String[] args) {
		SpringApplication.run(AiChatAssistantApplication.class, args);
	}

}

@Component
class StartupRunner implements CommandLineRunner {


	private final DocumentIndexer documentIndexer;

    StartupRunner(DocumentIndexer documentIndexer) {
        this.documentIndexer = documentIndexer;
    }


    @Override
	public void run(String... args) {
		documentIndexer.indexDocuments();
	}


}