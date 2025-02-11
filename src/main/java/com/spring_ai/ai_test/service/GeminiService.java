package com.spring_ai.ai_test.service;

import com.spring_ai.ai_test.EGeminiModels;
import com.spring_ai.ai_test.GeminiResponse;
import io.micrometer.common.util.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

/**
 * This service is different from the ones used in Spring AI because it only has compatibility with
 * Vertex, which has costs for using the Gemini API. So this service will call directly to Google AI Studio, with
 * free usage.
 */
@Service
public class GeminiService {

    // Constants:

    private static final String GEMINI_BASE_URL = "https://generativelanguage.googleapis.com/v1/models/%s:streamGenerateContent?key=%s";

    // Attributes:

    private String apiKey;
    private String geminiModel = EGeminiModels.GEMINI_2_0_FLASH.getModel();
    private String prompt = "Hello!";
    private String geminiFinalUrl;

    private WebClient webClient = null;

    public GeminiService(@Value("${gemini.api-key}") String apiKey) {
        this.apiKey = apiKey;
    }

    public GeminiService model(EGeminiModels model) {
        this.geminiModel = model.getModel();

        return this;
    }

    public GeminiService message(String prompt) {
        this.prompt = prompt;

        return this;
    }

    public GeminiService build() {
        this.geminiFinalUrl = GEMINI_BASE_URL.formatted(geminiModel, apiKey);

        this.webClient = WebClient.builder().baseUrl(geminiFinalUrl).build();

        return this;
    }

    public Flux<String> sendMessage(String prompt) {

        if (StringUtils.isBlank(geminiFinalUrl)) {
            throw new IllegalStateException("You need to build the service before using it!");
        }

        String requestBody = """
        {
          "contents": [
            {
              "parts": [
                { "text": "%s" }
              ]
            }
          ]
        }
        """.formatted(prompt);

        return webClient.post()
                .bodyValue(requestBody)
                .retrieve()
                .bodyToFlux(GeminiResponse.class) // Convertir la respuesta JSON en objeto
                .flatMap(response -> Flux.fromIterable(response.getCandidates()).take(1))
                .flatMap(candidate -> Flux.fromIterable(candidate.getContent().getParts()).take(1))
                .map(GeminiResponse.Part::getText)
                .doOnNext(System.out::println)
                .doOnError(error -> System.err.println("Error en streaming: " + error.getMessage()));
    }
}