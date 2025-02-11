package com.spring_ai.ai_test.chat_models;

import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.stereotype.Component;

@Component
public class DeepSeekChatModel implements IChatModel {

    /**
     * DeepSeek uses the OpenAi Chat model.
     */
    private final OpenAiChatModel chatModel;

    public DeepSeekChatModel(OpenAiChatModel chatModel) {
        this.chatModel = chatModel;
    }

    @Override
    public OpenAiChatModel getChatModel() {
        return chatModel;
    }
}
