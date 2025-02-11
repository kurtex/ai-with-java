package com.spring_ai.ai_test;


/**
 * The available AI models to be called in the application.
 */
public enum EChatModels {

    DEEPSEEK("deepSeekChatModel", "DEEPSEEK_API_KEY"),
    GEMINI(null, "GEMINI_API_KEY");


    /**
     * The name of the chat model corresponds to the name of the Bean
     */
    private String chatModelBeanName;

    private String apiKeyEnvironmentVariable;


    EChatModels(String chatModelBeanName, String apiKeyEnvironmentVariable) {
        this.chatModelBeanName = chatModelBeanName;
        this.apiKeyEnvironmentVariable = apiKeyEnvironmentVariable;
    }

    public String getChatModelBeanName() {
        return chatModelBeanName;
    }

    public String getApiKeyEnvironmentVariable() {
        return apiKeyEnvironmentVariable;
    }
}
