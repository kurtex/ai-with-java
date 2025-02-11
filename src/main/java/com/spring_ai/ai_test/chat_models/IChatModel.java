package com.spring_ai.ai_test.chat_models;

import org.springframework.ai.chat.model.ChatModel;

public interface IChatModel {

    /**
     * Gets the current chat model.
     *
     * @return The chat model.
     */
    ChatModel getChatModel();
}
