package com.spring_ai.ai_test;

import com.spring_ai.ai_test.service.AiModelService;
import com.spring_ai.ai_test.service.GeminiService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.Scanner;

@SpringBootApplication
public class AiTestApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(AiTestApplication.class, args);

		EChatModels modelUsed = EChatModels.GEMINI; // The model being used (change it if you want to use another one)

		var apiKey = System.getenv(modelUsed.getApiKeyEnvironmentVariable());

		if (apiKey == null) {
			throw new IllegalStateException("No API KEY found!");
		}

		String userInput = "";
		Scanner scanner = new Scanner(System.in);

		do {
			System.out.println("Enter a prompt:");
			userInput = scanner.nextLine();

			switch (modelUsed) {
				case GEMINI:

					GeminiService geminiService = context.getBean(GeminiService.class);

					geminiService.model(EGeminiModels.GEMINI_2_0_FLASH)
							.message(userInput)
							.build();

					geminiService.sendMessage(userInput)
							.blockLast();
					break;
				default:

					// Get AiModelService from context
					AiModelService service = context.getBean(AiModelService.class);

					// Create AiChatClient manually, passing the service through the constructor
					AiChatClient client = new AiChatClient(modelUsed, service);

					System.out.println(client.sendMessage(userInput));
					break;
			}
		} while (!userInput.equals("exit"));

		scanner.close();
		context.close();
	}
}
