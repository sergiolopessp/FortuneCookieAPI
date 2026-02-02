package com.example.fortunecookie.service;

import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.googleai.GoogleAiGeminiChatModel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class GeminiService {

    private final ChatLanguageModel chatModel;

    public GeminiService(@Value("${gemini.api-key}") String apiKey) {
        this.chatModel = GoogleAiGeminiChatModel.builder()
                .apiKey(apiKey)
                .modelName("gemini-3-flash-preview")
                .temperature(0.3)
                .build();
    }

    public String processQuery(String query) {
        return chatModel.generate(query);
    }
}
