package com.spring_ai.ai_test.service;

import com.spring_ai.ai_test.EChatModels;
import com.spring_ai.ai_test.chat_models.IChatModel;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

@Service
public class AiModelService {

    private final ApplicationContext applicationContext;

    public AiModelService(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    public ChatModel getChatModel(EChatModels chatModel) {
        IChatModel selectedChatModel = (IChatModel) applicationContext.getBean(chatModel.getChatModelBeanName());

        return selectedChatModel.getChatModel();
    }
}
