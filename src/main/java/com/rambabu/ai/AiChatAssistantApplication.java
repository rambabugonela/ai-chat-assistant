package com.rambabu.ai;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.RedisTemplate;

@SpringBootApplication
public class AiChatAssistantApplication {

	public static void main(String[] args) {
		SpringApplication.run(AiChatAssistantApplication.class, args);
	}

}
