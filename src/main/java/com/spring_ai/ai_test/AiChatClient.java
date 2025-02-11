package com.spring_ai.ai_test;

import com.spring_ai.ai_test.service.AiModelService;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.core.publisher.Flux;

public class AiChatClient {

    private final ChatClient client;

    @Autowired
    private AiModelService aiModelService;

    public AiChatClient(EChatModels model, AiModelService aiModelService) {
        ChatModel deepSeekChatModel = aiModelService.getChatModel(model);

        this.client = ChatClient.builder(deepSeekChatModel)
                .defaultSystem("""
                    You are a guy from Seville that always answer in Spanish with Seville accent.
                   \s
                    Always in the end of each phrase you finish it with: "Olé!".
               \s""")
                .build();
    }

    public String sendMessage(String message) {
        return client.prompt()
                .user(message)
                .call()
                .content();
    }
}
