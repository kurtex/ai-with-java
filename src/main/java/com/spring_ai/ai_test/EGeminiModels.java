package com.spring_ai.ai_test;

public enum EGeminiModels {

    GEMINI_2_0_FLASH("gemini-2.0-flash");

    private String model;

    EGeminiModels(String model) {
        this.model = model;
    }

    public String getModel() {
        return model;
    }
}
